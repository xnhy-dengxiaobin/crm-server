package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.BusiCustomerFollowDao;
import io.renren.modules.busi.entity.BusiCustomerFollowEntity;
import io.renren.modules.busi.service.BusiCustomerFollowService;


@Service("busiCustomerFollowService")
public class BusiCustomerFollowServiceImpl extends ServiceImpl<BusiCustomerFollowDao, BusiCustomerFollowEntity> implements BusiCustomerFollowService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiCustomerFollowEntity> page = this.page(
                new Query<BusiCustomerFollowEntity>().getPage(params),
                new QueryWrapper<BusiCustomerFollowEntity>()
        );

        return new PageUtils(page);
    }

}