package io.renren.modules.erp.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.erp.dao.BusiReportWqyDao;
import io.renren.modules.erp.entity.BusiReportWqyEntity;
import io.renren.modules.erp.service.BusiReportWqyService;


@Service("busiReportWqyService")
public class BusiReportWqyServiceImpl extends ServiceImpl<BusiReportWqyDao, BusiReportWqyEntity> implements BusiReportWqyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiReportWqyEntity> page = this.page(
                new Query<BusiReportWqyEntity>().getPage(params),
                new QueryWrapper<BusiReportWqyEntity>()
        );

        return new PageUtils(page);
    }

}