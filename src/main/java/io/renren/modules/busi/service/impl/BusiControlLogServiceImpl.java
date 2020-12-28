package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiControlLogDao;
import io.renren.modules.busi.entity.BusiControlLogEntity;
import io.renren.modules.busi.service.BusiControlLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiControlLogService")
public class BusiControlLogServiceImpl extends ServiceImpl<BusiControlLogDao, BusiControlLogEntity> implements BusiControlLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiControlLogEntity> page = this.page(
                new Query<BusiControlLogEntity>().getPage(params),
                new QueryWrapper<BusiControlLogEntity>()
        );

        return new PageUtils(page);
    }

}
