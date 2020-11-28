package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiCustomerProjectDao;
import io.renren.modules.busi.entity.BusiCustomerProjectEntity;
import io.renren.modules.busi.service.BusiCustomerProjectService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiCustomerProjectService")
public class BusiCustomerProjectServiceImpl extends ServiceImpl<BusiCustomerProjectDao, BusiCustomerProjectEntity> implements BusiCustomerProjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiCustomerProjectEntity> page = this.page(
                new Query<BusiCustomerProjectEntity>().getPage(params),
                new QueryWrapper<BusiCustomerProjectEntity>()
        );

        return new PageUtils(page);
    }

}
