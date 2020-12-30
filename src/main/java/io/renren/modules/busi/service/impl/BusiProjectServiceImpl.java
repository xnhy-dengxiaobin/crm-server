package io.renren.modules.busi.service.impl;

import io.renren.common.utils.ParamResolvor;
import io.renren.modules.busi.entity.MiddleTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.BusiProjectDao;
import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.busi.service.BusiProjectService;


@Service("busiProjectService")
public class BusiProjectServiceImpl extends ServiceImpl<BusiProjectDao, BusiProjectEntity> implements BusiProjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiProjectEntity> page = this.page(
                new Query<BusiProjectEntity>().getPage(params),
                new QueryWrapper<BusiProjectEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BusiProjectEntity> queryGroupList(Long userId) {
        return getBaseMapper().queryGroupList(userId);
    }

}