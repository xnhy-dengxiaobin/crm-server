package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.PrepareDao;
import io.renren.modules.busi.entity.PrepareEntity;
import io.renren.modules.busi.service.PrepareService;


@Service("prepareService")
public class PrepareServiceImpl extends ServiceImpl<PrepareDao, PrepareEntity> implements PrepareService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PrepareEntity> page = this.page(
                new Query<PrepareEntity>().getPage(params),
                new QueryWrapper<PrepareEntity>()
        );

        return new PageUtils(page);
    }

}