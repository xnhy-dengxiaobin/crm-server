package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.SysUserTokenDao;
import io.renren.modules.busi.entity.SysUserTokenEntity;
import io.renren.modules.busi.service.SysUserTokenService;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserTokenEntity> page = this.page(
                new Query<SysUserTokenEntity>().getPage(params),
                new QueryWrapper<SysUserTokenEntity>()
        );

        return new PageUtils(page);
    }

}