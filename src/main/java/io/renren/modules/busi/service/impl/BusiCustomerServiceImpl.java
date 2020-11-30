package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiCustomerDao;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.service.BusiCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busiCustomerService")
public class BusiCustomerServiceImpl extends ServiceImpl<BusiCustomerDao, BusiCustomerEntity> implements BusiCustomerService {

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
            busiCustomerEntityQueryWrapper.and(qw->qw.likeRight("name",params.get("condition")).or().likeRight("mobile_phone",params.get("condition")));
        }
        IPage<BusiCustomerEntity> page = this.page(
                new Query<BusiCustomerEntity>().getPage(params),
                busiCustomerEntityQueryWrapper
        );

        return new PageUtils(page);
    }

  @Override
  public IPage<BusiCustomerEntity> normalFollowPage(IPage<BusiCustomerEntity> page,String userId) {
    return baseMapper.normalFollowPage(page,userId);
  }

    @Override
    public List<BusiCustomerEntity> queryByPhone(Map<String, Object> params) {
        String mobilePhone = params.get("mobilePhone").toString();
        return getBaseMapper().selectByPhone(mobilePhone);
    }
}
