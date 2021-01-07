package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiBookingDao;
import io.renren.modules.busi.entity.BusiBookingEntity;
import io.renren.modules.busi.service.BusiBookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busiBookingService")
public class BusiBookingServiceImpl extends ServiceImpl<BusiBookingDao, BusiBookingEntity> implements BusiBookingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiBookingEntity> page = this.page(
                new Query<BusiBookingEntity>().getPage(params),
                new QueryWrapper<BusiBookingEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Object>> renchouGroup(Map param) {
        return baseMapper.renchouGroup(param);
    }

}
