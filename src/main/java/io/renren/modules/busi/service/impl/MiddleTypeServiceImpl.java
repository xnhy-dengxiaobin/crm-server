package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.MiddleTypeDao;
import io.renren.modules.busi.entity.MiddleTypeEntity;
import io.renren.modules.busi.service.MiddleTypeService;


@Service("middleTypeService")
public class MiddleTypeServiceImpl extends ServiceImpl<MiddleTypeDao, MiddleTypeEntity> implements MiddleTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MiddleTypeEntity> page = this.page(
                new Query<MiddleTypeEntity>().getPage(params),
                new QueryWrapper<MiddleTypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MiddleTypeEntity> qryLst(Map<String, Object> params) {
        return getBaseMapper().selectByMap(params);
    }
}