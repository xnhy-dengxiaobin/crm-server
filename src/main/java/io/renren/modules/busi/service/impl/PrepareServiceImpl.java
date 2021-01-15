package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.modules.busi.bean.BusiStatusEnum;
import io.renren.modules.busi.constant.Constant;
import io.renren.modules.busi.dao.BusiCustomerDao;
import io.renren.modules.busi.dao.BusiUserProjectDao;
import io.renren.modules.busi.dao.CustomerStatusLogDao;
import io.renren.modules.busi.entity.*;
import io.renren.modules.busi.service.CustomerStatusLogService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.PrepareDao;
import io.renren.modules.busi.service.PrepareService;
import org.springframework.transaction.annotation.Transactional;


@Service("prepareService")
public class PrepareServiceImpl extends ServiceImpl<PrepareDao, PrepareEntity> implements PrepareService {

    @Autowired
    private CustomerStatusLogService customerStatusLogService;

    @Autowired
    private CustomerStatusLogDao customerStatusLogDao;

    @Autowired
    private BusiUserProjectDao busiUserProjectDao;

    @Autowired
    private BusiCustomerDao busiCustomerDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String mobile = (String) params.get("mobile");
        String name = (String) params.get("name");
        String status = (String) params.get("status");
        IPage<PrepareEntity> page = this.page(
                new Query<PrepareEntity>().getPage(params),
                new QueryWrapper<PrepareEntity>().like(StringUtils.isNotBlank(mobile), "mobile", mobile).like(StringUtils.isNotBlank(name), "name", name)
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils lstPage(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long

        List<PrepareEntity> list = getBaseMapper().slct(params);
        long cnt = getBaseMapper().cnt(params);

        Page<PrepareEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(list);

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean check(PrepareCheckEntity checks) {
        for (Integer id : checks.getIds()) {
            PrepareEntity pe = new PrepareEntity();
            pe.setId(id);
            pe.setStatus(checks.getStatus());
            pe.setUpdatedTime(new Date());
            getBaseMapper().updateById(pe);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String wxSave(PrepareEntity prepare, long userId) {
        String msg = "";
        List<BusiCustomerEntity> customers = busiCustomerDao.selectByMap(new HashedMap() {{
            put("mobile_phone", prepare.getMobile());
        }});
        if (CollectionUtils.isNotEmpty(customers)) {
            BusiCustomerEntity cus = customers.get(0);
            if (cus.getInvalid() == 0) {
                msg = "拒收无效";
                prepare.setStatus(BusiStatusEnum.PREPARE_REJECT.getCode());
                prepare.setUserId(userId + "");
                prepare.setCreatedTime(new Date());
                prepare.setStatusUpdatedTime(new Date());
                prepare.setUpdatedTime(new Date());
                prepare.setReason("案场老客户");
                getBaseMapper().insert(prepare);

                //增加状态变更日志
                CustomerStatusLogEntity customerStatusLogEntity = customerStatusLogService.prepareReject(prepare.getCustomerId(), prepare.getId(), prepare.getReason(), false);
                customerStatusLogEntity.setUserId(Long.valueOf(userId).intValue());
                customerStatusLogDao.insert(customerStatusLogEntity);

                return msg;
            }
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", prepare.getMobile());
        params.put("user_id", userId);
        List<PrepareEntity> pes = getBaseMapper().selectList(new QueryWrapper<PrepareEntity>()
                .eq("user_id", userId).eq("mobile", prepare.getMobile()));
        List<PrepareEntity> peso = getBaseMapper().selectList(new QueryWrapper<PrepareEntity>()
                .ne("user_id", userId).eq("mobile", prepare.getMobile()).ge("status", 0));
        if (pes.size() > 0 && peso.size() == 0) {
            if (pes.get(0).getStatus() >= 0) {
                prepare.setId(pes.get(0).getId());
                prepare.setUpdatedTime(new Date());
                getBaseMapper().updateById(prepare);
                msg = "已重新报备";
            } else {
                prepare.setUpdatedTime(new Date());
                prepare.setStatusUpdatedTime(new Date());
                prepare.setStatus(0);
                prepare.setId(pes.get(0).getId());
                getBaseMapper().updateById(prepare);
                msg = "已重新报备";
            }

            //增加状态变更日志
            CustomerStatusLogEntity customerStatusLogEntity = customerStatusLogService.prepareAudit(prepare.getCustomerId(), prepare.getId(), true);
            customerStatusLogEntity.setUserId(Long.valueOf(userId).intValue());
            customerStatusLogDao.insert(customerStatusLogEntity);
        } else if (peso.size() > 0 && pes.size() == 0) {
            prepare.setStatus(-10);
            prepare.setCreatedTime(new Date());
            prepare.setStatusUpdatedTime(new Date());
            prepare.setUpdatedTime(new Date());
            getBaseMapper().insert(prepare);
            msg = "手机号拒收无效";

            //增加状态变更日志
            CustomerStatusLogEntity customerStatusLogEntity = customerStatusLogService.prepareReject(prepare.getCustomerId(), prepare.getId(), "拒收无效", false);
            customerStatusLogEntity.setUserId(Long.valueOf(userId).intValue());
            customerStatusLogDao.insert(customerStatusLogEntity);
        } else if (peso.size() > 0 && pes.size() > 0) {
            msg = "手机号拒收无效";
        } else {
            prepare.setCreatedTime(new Date());
            prepare.setUpdatedTime(new Date());
            prepare.setStatusUpdatedTime(new Date());
            prepare.setExpired(DateUtils.addDateDays(new Date(), Constant.prepareValidPeriod));
            getBaseMapper().insert(prepare);
            msg = "报备成功";

            //增加状态变更日志
            CustomerStatusLogEntity customerStatusLogEntity = customerStatusLogService.prepareAudit(prepare.getCustomerId(), prepare.getId(), false);
            customerStatusLogEntity.setUserId(Long.valueOf(userId).intValue());
            customerStatusLogDao.insert(customerStatusLogEntity);
        }
        return msg;
    }

    @Override
    public PrepareEntity gtById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getBaseMapper().gtById(null, id);
    }

    @Override
    public PageUtils qryPage(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long

        List<PrepareEntity> slct = getBaseMapper().selectCheckList(params);
        ;
        Long cnt = getBaseMapper().checkCnt(params);
        Page<PrepareEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(slct);

        return new PageUtils(page);
    }

    @Override
    public PageUtils listPage4Admin(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long

        //获取当前用户所有有权限的楼盘
        /*List<BusiProjectEntity> userProjects = busiUserProjectDao.selectProjectByUser(ParamResolvor.getLong(params, "userId"));
        List<Long> projectIds = new ArrayList<>();
        for(BusiProjectEntity up : userProjects){
            List<BusiUserProjectEntity> children = busiUserProjectDao.selectList(new QueryWrapper<BusiUserProjectEntity>().eq("parent_id", up.getId()));
            if(CollectionUtils.isEmpty(children)){
                continue;
            }
            for(BusiProjectEntity child : userProjects){
                projectIds.add(Long.valueOf(child.getId()));
            }
        }
        params.put("projectIds", projectIds);*/
        List<PrepareEntity> list = getBaseMapper().selectPage4Admin(params);
        long cnt = getBaseMapper().count4Admin(params);

        Page<PrepareEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(list);

        return new PageUtils(page);
    }

    @Override
    public void refreshExpired(List<Integer> ids) {
        for (Integer id : ids) {
            PrepareEntity prepareEntity = getBaseMapper().selectById(id);
            PrepareEntity p = new PrepareEntity();
            p.setId(id);
            Date d = prepareEntity.getExpired() == null ? new Date() : prepareEntity.getExpired();
            p.setExpired(DateUtils.addDateDays(d, Constant.channelGranteePeriod));
            getBaseMapper().updateById(p);
        }
    }
}