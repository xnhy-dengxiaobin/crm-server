package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.BusiHouseDao;
import io.renren.modules.busi.entity.BusiHouseEntity;
import io.renren.modules.busi.service.BusiHouseService;


@Service("busiHouseService")
public class BusiHouseServiceImpl extends ServiceImpl<BusiHouseDao, BusiHouseEntity> implements BusiHouseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiHouseEntity> page = this.page(
                new Query<BusiHouseEntity>().getPage(params),
                new QueryWrapper<BusiHouseEntity>()
        );

        return new PageUtils(page);
    }

}