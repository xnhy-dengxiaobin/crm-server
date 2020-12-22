package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.CustomerStatusLogDao;
import io.renren.modules.busi.entity.CustomerStatusLogEntity;
import io.renren.modules.busi.service.CustomerStatusLogService;


@Service("customerStatusLogService")
public class CustomerStatusLogServiceImpl extends ServiceImpl<CustomerStatusLogDao, CustomerStatusLogEntity> implements CustomerStatusLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CustomerStatusLogEntity> page = this.page(
                new Query<CustomerStatusLogEntity>().getPage(params),
                new QueryWrapper<CustomerStatusLogEntity>()
        );

        return new PageUtils(page);
    }

}