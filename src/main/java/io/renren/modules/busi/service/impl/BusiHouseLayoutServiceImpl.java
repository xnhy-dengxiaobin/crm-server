package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.HouseLayoutDao;
import io.renren.modules.busi.entity.HouseLayoutEntity;
import io.renren.modules.busi.service.BusiHouseLayoutService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiHouseLayoutService")
public class BusiHouseLayoutServiceImpl extends ServiceImpl<HouseLayoutDao, HouseLayoutEntity> implements BusiHouseLayoutService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseLayoutEntity> page = this.page(
                new Query<HouseLayoutEntity>().getPage(params),
                new QueryWrapper<HouseLayoutEntity>()
        );

        return new PageUtils(page);
    }

}
