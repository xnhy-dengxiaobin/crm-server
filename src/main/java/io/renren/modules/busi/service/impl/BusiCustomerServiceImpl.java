package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiCustomerDao;
import io.renren.modules.busi.dao.ReceptionDao;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.service.BusiCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("busiCustomerService")
public class BusiCustomerServiceImpl extends ServiceImpl<BusiCustomerDao, BusiCustomerEntity> implements BusiCustomerService {

    @Autowired
    private ReceptionDao receptionDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BusiCustomerEntity> busiCustomerEntityQueryWrapper = new QueryWrapper<>();
        if(params.get("matchUserId") != null){
            busiCustomerEntityQueryWrapper.eq("match_user_id",params.get("matchUserId"));
        }
        if(params.get("followUserId") != null){
            busiCustomerEntityQueryWrapper.and(qw->qw.ne("follow_user_id",params.get("followUserId")).or().isNull("follow_user_id"));
        }
        if(params.get("condition") != null && !"".equals(params.get("condition"))){
            busiCustomerEntityQueryWrapper.and(qw->qw.like("name",params.get("condition")).or().like("mobile_phone",params.get("condition")));
        }
        if(params.get("desc") != null){
            busiCustomerEntityQueryWrapper.orderByDesc(params.get("desc").toString());
        }
        IPage<BusiCustomerEntity> page = this.page(
                new Query<BusiCustomerEntity>().getPage(params),
                busiCustomerEntityQueryWrapper
        );

        return new PageUtils(page);
    }

  @Override
  public IPage<BusiCustomerEntity> normalFollowPage(IPage<BusiCustomerEntity> page,String userId,String projectId) {
    return baseMapper.normalFollowPage(page,userId,projectId);
  }

  @Override
  public IPage<BusiCustomerEntity> timeoutPage(IPage<BusiCustomerEntity> page, String userId, String projectId,String keywords) {
    return baseMapper.timeoutPage(page,userId,projectId,keywords);
  }

  @Override
  public IPage<BusiCustomerEntity> publicPage(IPage<BusiCustomerEntity> page, String projectId,String keywords, Integer stt,Long matchUserId) {
    return baseMapper.publicPage(page,projectId,keywords,stt,matchUserId);
  }

  @Override
  public long countNormal(Object projectId) {
    return baseMapper.countNormal(projectId);
  }

  @Override
  public long countTimeout(Object projectId) {
    return baseMapper.countTimeout(projectId);
  }

  @Override
    public List<BusiCustomerEntity> queryByPhone(Map<String, Object> params) {
        String mobilePhone = params.get("mobilePhone").toString();
        return getBaseMapper().selectByPhone(mobilePhone);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void perfect(BusiCustomerEntity busiCustomerEntity) {
        getBaseMapper().updateById(busiCustomerEntity);
        ReceptionEntity receptionEntity = new ReceptionEntity();
        receptionEntity.setStatus(1);//已处理
        receptionDao.update(receptionEntity,
                new UpdateWrapper<ReceptionEntity>()
                        .eq("customer_id", busiCustomerEntity.getId())
                        .eq("saler_id", busiCustomerEntity.getMatchUserId()));
    }

    @Override
    public List<Map> groupByDateCountWeek(String endDate,Integer projectId){
        List<Map> maps = baseMapper.groupByDateCountWeek(endDate,projectId);
        return maps;
    }

    @Override
    public List<Map> groupByDateCountDay(String endDate,Integer projectId){
        List<Map> maps = baseMapper.groupByDateCountDay(endDate,projectId);
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
