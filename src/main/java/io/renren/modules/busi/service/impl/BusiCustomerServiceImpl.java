package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<BusiCustomerEntity> queryByPhone(Map<String, Object> params) {
        String mobilePhone = params.get("mobilePhone").toString();
        return getBaseMapper().selectByPhone(mobilePhone);
    }
}