package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.BusiCustomerDao;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.service.BusiCustomerService;


@Service("busiCustomerService")
public class BusiCustomerServiceImpl extends ServiceImpl<BusiCustomerDao, BusiCustomerEntity> implements BusiCustomerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiCustomerEntity> page = this.page(
                new Query<BusiCustomerEntity>().getPage(params),
                new QueryWrapper<BusiCustomerEntity>()
        );

        return new PageUtils(page);
    }

  @Override
  public IPage<BusiCustomerEntity> normalFollowPage(IPage<BusiCustomerEntity> page,String userId) {
    return baseMapper.normalFollowPage(page,userId);
  }

}
