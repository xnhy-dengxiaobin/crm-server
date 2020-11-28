package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.service.BusiHouseLayoutService;


@Service("busiHouseLayoutService")
public class BusiHouseLayoutServiceImpl extends ServiceImpl<BusiHouseLayoutDao, BusiHouseLayoutEntity> implements BusiHouseLayoutService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiHouseLayoutEntity> page = this.page(
                new Query<BusiHouseLayoutEntity>().getPage(params),
                new QueryWrapper<BusiHouseLayoutEntity>()
        );

        return new PageUtils(page);
    }

}