package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiCustomerDao;
import io.renren.modules.busi.dao.BusiCustomerRoamDao;
import io.renren.modules.busi.dao.ReceptionDao;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.BusiCustomerRoamEntity;
import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.service.ReceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("receptionService")
public class ReceptionServiceImpl extends ServiceImpl<ReceptionDao, ReceptionEntity> implements ReceptionService {

    @Autowired
    private BusiCustomerDao busiCustomerDao;

    @Autowired
    private BusiCustomerRoamDao busiCustomerRoamDao;

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
    public void saveReception(ReceptionEntity receptionEntity, BusiCustomerEntity busiCustomerEntity, BusiCustomerRoamEntity busiCustomerRoamEntity) {
        BusiCustomerEntity cus = busiCustomerDao.selectOne(new QueryWrapper<BusiCustomerEntity>().eq("mobile_phone", busiCustomerEntity.getMobilePhone()));

        if ((null != cus && cus.getId() > 0) || busiCustomerEntity.getId() > 0) {

            if (!busiCustomerEntity.getMatchUserId().equals(cus.getMatchUserId())) {
                //换了置业顾问，重新设置分配时间
                busiCustomerEntity.setMatchUserTime(new Date());
                //换了置业顾问，增加转介路径
                busiCustomerRoamEntity.setCustomerId(cus.getId());
                busiCustomerRoamDao.insert(busiCustomerRoamEntity);
            }

            busiCustomerDao.updateById(busiCustomerEntity);
            if (null == busiCustomerEntity.getId() || busiCustomerEntity.getId() <= 0) {
                busiCustomerEntity.setId(cus.getId());
            }
            receptionEntity.setIsNew(0);//老客户
        } else {
            busiCustomerEntity.setMatchUserTime(new Date());
            busiCustomerEntity.setProjectId(receptionEntity.getProjectId());
            busiCustomerEntity.setStatus(1);
            busiCustomerDao.insert(busiCustomerEntity);
            receptionEntity.setIsNew(1);//新客户

            //新客户，增加转介路径
            busiCustomerRoamEntity.setCustomerId(busiCustomerEntity.getId());
            busiCustomerRoamDao.insert(busiCustomerRoamEntity);
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
    public List<Map> groupByDateCount(String endDate,Integer projectId){
        List<Map> maps = baseMapper.groupByDateCount(endDate,projectId);
        return maps;
    }


    @Override
    public List<Map> groupByDateCountMonth(String endDate,Integer projectId){
        List<Map> maps = baseMapper.groupByDateCountMonth(endDate,projectId);
        return maps;
    }
    @Override
    public List<Map> groupByDateCountYear(String endDate,Integer projectId){
        List<Map> maps = baseMapper.groupByDateCountYear(endDate,projectId);
        return maps;
    }


}
