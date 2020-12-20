package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SetupDao;
import io.renren.modules.sys.entity.SetupEntity;
import io.renren.modules.sys.service.SetupService;


@Service("setupService")
public class SetupServiceImpl extends ServiceImpl<SetupDao, SetupEntity> implements SetupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SetupEntity> page = this.page(
                new Query<SetupEntity>().getPage(params),
                new QueryWrapper<SetupEntity>()
        );

        return new PageUtils(page);
    }

}