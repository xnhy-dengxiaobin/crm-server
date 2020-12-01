package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiCustomerRoamDao;
import io.renren.modules.busi.entity.BusiCustomerRoamEntity;
import io.renren.modules.busi.service.BusiCustomerRoamService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiCustomerRoamService")
public class BusiCustomerRoamServiceImpl extends ServiceImpl<BusiCustomerRoamDao, BusiCustomerRoamEntity> implements BusiCustomerRoamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiCustomerRoamEntity> page = this.page(
                new Query<BusiCustomerRoamEntity>().getPage(params),
                new QueryWrapper<BusiCustomerRoamEntity>()
        );

        return new PageUtils(page);
    }

}