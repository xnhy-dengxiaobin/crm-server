package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.ReceptionDao;
import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.service.ReceptionService;


@Service("receptionService")
public class ReceptionServiceImpl extends ServiceImpl<ReceptionDao, ReceptionEntity> implements ReceptionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReceptionEntity> page = this.page(
                new Query<ReceptionEntity>().getPage(params),
                new QueryWrapper<ReceptionEntity>()
        );

        return new PageUtils(page);
    }

}