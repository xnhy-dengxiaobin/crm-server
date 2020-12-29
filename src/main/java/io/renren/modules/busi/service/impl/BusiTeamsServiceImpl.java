package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiTeamsDao;
import io.renren.modules.busi.entity.BusiTeamsEntity;
import io.renren.modules.busi.service.BusiTeamsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiTeamsService")
public class BusiTeamsServiceImpl extends ServiceImpl<BusiTeamsDao, BusiTeamsEntity> implements BusiTeamsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiTeamsEntity> page = this.page(
                new Query<BusiTeamsEntity>().getPage(params),
                new QueryWrapper<BusiTeamsEntity>()
        );

        return new PageUtils(page);
    }

}
