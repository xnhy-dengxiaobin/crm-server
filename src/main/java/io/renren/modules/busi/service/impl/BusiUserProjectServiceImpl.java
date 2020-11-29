package io.renren.modules.busi.service.impl;

import io.renren.modules.busi.entity.BusiProjectEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.BusiUserProjectDao;
import io.renren.modules.busi.entity.BusiUserProjectEntity;
import io.renren.modules.busi.service.BusiUserProjectService;


@Service("busiUserProjectService")
public class BusiUserProjectServiceImpl extends ServiceImpl<BusiUserProjectDao, BusiUserProjectEntity> implements BusiUserProjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiUserProjectEntity> page = this.page(
                new Query<BusiUserProjectEntity>().getPage(params),
                new QueryWrapper<BusiUserProjectEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BusiProjectEntity> queryProjectByUser(Long userId) {
        //TODO: 如果是admin, 返回全部用户
        if (userId == 1) {
            return getBaseMapper().selectProjectByAdmin();
        }
        return getBaseMapper().selectProjectByUser(userId);
    }
}