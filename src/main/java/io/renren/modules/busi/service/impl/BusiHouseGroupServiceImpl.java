package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiHouseGroupDao;
import io.renren.modules.busi.entity.BusiHouseGroupEntity;
import io.renren.modules.busi.service.BusiHouseGroupService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiHouseGroupService")
public class BusiHouseGroupServiceImpl extends ServiceImpl<BusiHouseGroupDao, BusiHouseGroupEntity> implements BusiHouseGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiHouseGroupEntity> page = this.page(
                new Query<BusiHouseGroupEntity>().getPage(params),
                new QueryWrapper<BusiHouseGroupEntity>()
        );

        return new PageUtils(page);
    }

}
