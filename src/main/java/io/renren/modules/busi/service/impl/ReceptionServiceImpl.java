package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.modules.busi.bean.BusiStatusEnum;
import io.renren.modules.busi.constant.Constant;
import io.renren.modules.busi.dao.*;
import io.renren.modules.busi.entity.*;
import io.renren.modules.busi.exception.BusiException;
import io.renren.modules.busi.service.CustomerStatusLogService;
import io.renren.modules.busi.service.ReceptionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("receptionService")
public class ReceptionServiceImpl extends ServiceImpl<ReceptionDao, ReceptionEntity> implements ReceptionService {

    @Autowired
    private BusiCustomerDao busiCustomerDao;

    @Autowired
    private BusiCustomerRoamDao busiCustomerRoamDao;

    @Autowired
    private PrepareDao prepareDao;

    @Autowired
    private CustomerStatusLogDao customerStatusLogDao;

    @Autowired
    private CustomerStatusLogService customerStatusLogService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<ReceptionEntity> receptionEntityQueryWrapper = new QueryWrapper<>();
        if (params.get("salerId") != null) {
            receptionEntityQueryWrapper.eq("saler_id", params.get("salerId"));
        }
        IPage<ReceptionEntity> page = this.page(
                new Query<ReceptionEntity>().getPage(params),
                receptionEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveReception(ReceptionEntity receptionEntity, BusiCustomerEntity busiCustomerEntity,
                              BusiCustomerRoamEntity busiCustomerRoamEntity, Integer prepareId) {
        BusiCustomerEntity cus = busiCustomerDao.selectOne(new QueryWrapper<BusiCustomerEntity>().eq("mobile_phone", busiCustomerEntity.getMobilePhone()));

        //如果没有客户，说明是初次到访，将其它相同电话号码的客户报备置为手工无效；添加状态变更日志人工确客：接收；添加经纪人保护期。
        //如果客户已经存在->
        //属于当前经纪人->检查客户状态，是来访且在保护期内:更新经纪人保护期，是来访在保护期外：提示无效，在来访之上：不用做什么
        //不属于当前经纪人->提示项目老客户
        if ((null != cus && cus.getId() > 0) || busiCustomerEntity.getId() > 0) {
            busiCustomerEntity.setId(cus.getId());

            //需要修改的信息
            BusiCustomerEntity updatingCus = new BusiCustomerEntity();
            updatingCus.setId(cus.getId());
            updatingCus.setComeCount((cus.getComeCount() == null ? 0 : cus.getComeCount()) + 1);

            if (prepareId > 0) {
                PrepareEntity prepareEntity = prepareDao.gtById(busiCustomerEntity.getSourceUserId(), Long.valueOf(prepareId).intValue());
                if (null == prepareEntity) {
                    throw new BusiException("您没有报备过这个客户");
                }

                //如果是渠道带过来的，首先检查客户是不是该渠道的
                List<PrepareEntity> prepares = prepareDao.selectByMap(new HashedMap() {{
                    put("customer_id", cus.getId());
                }});
                boolean mine = false;
                for (PrepareEntity p : prepares) {
                    if (!p.getId().equals(prepareId) && p.getStatus() == BusiStatusEnum.PREPARE_OK.getCode()) {
                        throw new BusiException("该客户已由别的经纪人带访并且还处于保护期内");
                    }
                    if (p.getId().equals(prepareId) && p.getStatus() == BusiStatusEnum.PREPARE_OK.getCode()) {
                        mine = true;
                    }
                }

                //已经带访过客户
                //客户状态是来访，在保护期内，更新保护期
                PrepareEntity pp = new PrepareEntity();
                pp.setId(prepareEntity.getId());
                Date expired = DateUtils.addDateDays(new Date(), 30);
                pp.setExpired(expired);
                pp.setStatus(BusiStatusEnum.PREPARE_OK.getCode());
                pp.setMatchUserId(busiCustomerEntity.getMatchUserId());
                pp.setMatchUserName(busiCustomerEntity.getMatchUserName());
                //以前不是自己带访的，关联客户
                if (!mine) {
                    pp.setCustomerId(cus.getId());
                    prepareDao.updateById(pp);

                    //添加转介路径
                    BusiCustomerRoamEntity roamEntity = new BusiCustomerRoamEntity();
                    roamEntity.setUserId(busiCustomerEntity.getSourceUserId());
                    roamEntity.setCustomerId(cus.getId());
                    roamEntity.setRemark("经纪人" + busiCustomerEntity.getSourceUserName() + "带访");
                    roamEntity.setCreateTime(new Date());
                    busiCustomerRoamDao.insert(roamEntity);
                }
                prepareDao.updateById(pp);

                //记录保护期更新日志
                CustomerStatusLogEntity customerStatusLogEntity = customerStatusLogService.periodChangeVisited(cus.getId(), prepareId, expired);
                customerStatusLogEntity.setUserId(receptionEntity.getReceptionistId());
                customerStatusLogDao.insert(customerStatusLogEntity);
            }

            if (!busiCustomerEntity.getMatchUserId().equals(cus.getMatchUserId())) {
                //换了置业顾问，重新设置分配时间
                updatingCus.setMatchUserTime(new Date());

                //换了置业顾问，增加转介路径
                busiCustomerRoamEntity.setCustomerId(cus.getId());
                busiCustomerRoamDao.insert(busiCustomerRoamEntity);
            }

            if (null == busiCustomerEntity.getId() || busiCustomerEntity.getId() <= 0) {
                busiCustomerEntity.setId(cus.getId());
            }
            receptionEntity.setIsNew(0);//老客户

            busiCustomerDao.updateById(updatingCus);
        } else {
            Date now = new Date();
            busiCustomerEntity.setMatchUserTime(now);
            busiCustomerEntity.setProjectId(receptionEntity.getProjectId());
            busiCustomerEntity.setStatus(1);
            busiCustomerEntity.setBusiStatus(BusiStatusEnum.CUS_VISITED.getCode());
            busiCustomerEntity.setBusiStatusUpdatedTime(now);
            busiCustomerDao.insert(busiCustomerEntity);
            receptionEntity.setIsNew(1);//新客户

            //新客户，增加转介路径
            busiCustomerRoamEntity.setCustomerId(busiCustomerEntity.getId());
            busiCustomerRoamDao.insert(busiCustomerRoamEntity);

            //处理报备。关联报备记录，将其它相同号码的报备记录设置为无效
            if (prepareId > 0) {
                PrepareEntity okPrepare = prepareDao.selectById(prepareId);

                //先将其它相同客户号码的有效报备置为手工无效
                List<Integer> status = new ArrayList<Integer>();
                status.add(0);
                status.add(10);
                List<PrepareEntity> prepareEntities = prepareDao.selectList(new QueryWrapper<PrepareEntity>().in("status", status).eq("mobile", okPrepare.getMobile()));
                if (CollectionUtils.isNotEmpty(prepareEntities)) {
                    for (PrepareEntity p : prepareEntities) {
                        if (p.getId() == prepareId) {
                            continue;
                        }
                        PrepareEntity pEntity = new PrepareEntity();
                        pEntity.setId(p.getId());
                        pEntity.setStatus(-20);
                        prepareDao.updateById(pEntity);

                        //状态变更日志，手工无效
                        CustomerStatusLogEntity visitedLog = customerStatusLogService.mannulReject(busiCustomerEntity.getId(), Long.valueOf(prepareId).intValue(), Constant.MANNUL_REJECT_REASON);
                        visitedLog.setUserId(receptionEntity.getReceptionistId());
                        customerStatusLogDao.insert(visitedLog);
                    }
                }

                //将当前报备设置为有效且关联客户id
                PrepareEntity prepareEntity = new PrepareEntity();
                prepareEntity.setId(prepareId);
                prepareEntity.setCustomerId(busiCustomerEntity.getId());
                prepareEntity.setStatus(10);
                //到访后的保护期
                Date expired = DateUtils.addDateDays(now, Constant.channelGranteePeriod);
                prepareEntity.setExpired(expired);
                prepareDao.updateById(prepareEntity);

                //记录状态变更日志
                //来访-来访
                CustomerStatusLogEntity visitedLog = customerStatusLogService.visited(busiCustomerEntity.getId(), Long.valueOf(prepareId).intValue());
                visitedLog.setUserId(receptionEntity.getReceptionistId());
                customerStatusLogDao.insert(visitedLog);
                //人工确客-接收
                CustomerStatusLogEntity mannulLog = customerStatusLogService.mannulReceive(busiCustomerEntity.getId(), Long.valueOf(prepareId).intValue(), receptionEntity.getReceptionistName());
                mannulLog.setUserId(receptionEntity.getReceptionistId());
                customerStatusLogDao.insert(mannulLog);
            }
        }

        receptionEntity.setCustomerId(busiCustomerEntity.getId());
        getBaseMapper().insert(receptionEntity);
    }

    @Override
    public PageUtils qryPage(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long

        List<ReceptionEntity> slct = getBaseMapper().slct(params);
        Long cnt = getBaseMapper().cnt(params);
        Page<ReceptionEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(slct);

        return new PageUtils(page);
    }

    @Override
    public PageUtils listBySalerId(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long


        List<ReceptionEntity> list = getBaseMapper().listBySalerId(params);
        Long cnt = getBaseMapper().listBySalerIdCnt(params);
        Page<ReceptionEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(list);

        return new PageUtils(page);
    }

    @Override
    public List<Map> groupByDateCount(String endDate, String[] projectIds) {
        List<Map> maps = baseMapper.groupByDateCount(endDate, projectIds);
        return maps;
    }


    @Override
    public List<Map> groupByDateCountMonth(String endDate, String[] projectIds) {
        List<Map> maps = baseMapper.groupByDateCountMonth(endDate, projectIds);
        return maps;
    }

    @Override
    public List<Map> groupByDateCountYear(String endDate, String[] projectIds) {
        List<Map> maps = baseMapper.groupByDateCountYear(endDate, projectIds);
        return maps;
    }


}
