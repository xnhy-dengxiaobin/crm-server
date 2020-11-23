package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.SysSetupDao;
import io.renren.modules.busi.entity.SysSetupEntity;
import io.renren.modules.busi.service.SysSetupService;


@Service("sysSetupService")
public class SysSetupServiceImpl extends ServiceImpl<SysSetupDao, SysSetupEntity> implements SysSetupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysSetupEntity> page = this.page(
                new Query<SysSetupEntity>().getPage(params),
                new QueryWrapper<SysSetupEntity>()
        );

        return new PageUtils(page);
    }

}