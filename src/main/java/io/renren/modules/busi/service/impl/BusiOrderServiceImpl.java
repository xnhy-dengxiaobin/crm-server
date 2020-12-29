package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiOrderDao;
import io.renren.modules.busi.entity.BusiOrderEntity;
import io.renren.modules.busi.service.BusiOrderService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiOrderService")
public class BusiOrderServiceImpl extends ServiceImpl<BusiOrderDao, BusiOrderEntity> implements BusiOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiOrderEntity> page = this.page(
                new Query<BusiOrderEntity>().getPage(params),
                new QueryWrapper<BusiOrderEntity>()
        );

        return new PageUtils(page);
    }

}
