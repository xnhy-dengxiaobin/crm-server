package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.CustomerStatusLogDao;
import io.renren.modules.busi.entity.CustomerStatusLogEntity;
import io.renren.modules.busi.service.CustomerStatusLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


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

    @Override
    public List<CustomerStatusLogEntity> queryList(Map<String, Object> params) {
        Integer prepareId = ParamResolvor.getInt(params, "prepareId");
        Integer customerId = ParamResolvor.getIntAsDefault(params, "customerId", 0);
        return getBaseMapper().selectList(new QueryWrapper<CustomerStatusLogEntity>()
                .eq("prepare_id", prepareId)
                .or()
                .eq(customerId > 0, "customer_id", customerId).orderByDesc("found_time")
        );
    }
}