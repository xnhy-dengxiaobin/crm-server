package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.BusiCustomerOrderDao;
import io.renren.modules.busi.entity.BusiCustomerOrderEntity;
import io.renren.modules.busi.service.BusiCustomerOrderService;


@Service("busiCustomerOrderService")
public class BusiCustomerOrderServiceImpl extends ServiceImpl<BusiCustomerOrderDao, BusiCustomerOrderEntity> implements BusiCustomerOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiCustomerOrderEntity> page = this.page(
                new Query<BusiCustomerOrderEntity>().getPage(params),
                new QueryWrapper<BusiCustomerOrderEntity>()
        );

        return new PageUtils(page);
    }

}