package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.BusiCustomerBookingDao;
import io.renren.modules.busi.entity.BusiCustomerBookingEntity;
import io.renren.modules.busi.service.BusiCustomerBookingService;


@Service("busiCustomerBookingService")
public class BusiCustomerBookingServiceImpl extends ServiceImpl<BusiCustomerBookingDao, BusiCustomerBookingEntity> implements BusiCustomerBookingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiCustomerBookingEntity> page = this.page(
                new Query<BusiCustomerBookingEntity>().getPage(params),
                new QueryWrapper<BusiCustomerBookingEntity>()
        );

        return new PageUtils(page);
    }

}