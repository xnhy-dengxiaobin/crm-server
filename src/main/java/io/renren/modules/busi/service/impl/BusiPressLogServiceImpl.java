package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiPressLogDao;
import io.renren.modules.busi.entity.BusiPressLogEntity;
import io.renren.modules.busi.service.BusiPressLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiPressLogService")
public class BusiPressLogServiceImpl extends ServiceImpl<BusiPressLogDao, BusiPressLogEntity> implements BusiPressLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiPressLogEntity> page = this.page(
                new Query<BusiPressLogEntity>().getPage(params),
                new QueryWrapper<BusiPressLogEntity>()
        );

        return new PageUtils(page);
    }

}
