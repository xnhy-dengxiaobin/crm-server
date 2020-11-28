package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.BusiCustomerRelevanceDao;
import io.renren.modules.busi.entity.BusiCustomerRelevanceEntity;
import io.renren.modules.busi.service.BusiCustomerRelevanceService;


@Service("busiCustomerRelevanceService")
public class BusiCustomerRelevanceServiceImpl extends ServiceImpl<BusiCustomerRelevanceDao, BusiCustomerRelevanceEntity> implements BusiCustomerRelevanceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiCustomerRelevanceEntity> page = this.page(
                new Query<BusiCustomerRelevanceEntity>().getPage(params),
                new QueryWrapper<BusiCustomerRelevanceEntity>()
        );

        return new PageUtils(page);
    }

}