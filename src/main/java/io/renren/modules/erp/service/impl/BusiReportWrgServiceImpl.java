package io.renren.modules.erp.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.erp.dao.BusiReportWrgDao;
import io.renren.modules.erp.entity.BusiReportWrgEntity;
import io.renren.modules.erp.service.BusiReportWrgService;


@Service("busiReportWrgService")
public class BusiReportWrgServiceImpl extends ServiceImpl<BusiReportWrgDao, BusiReportWrgEntity> implements BusiReportWrgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiReportWrgEntity> page = this.page(
                new Query<BusiReportWrgEntity>().getPage(params),
                new QueryWrapper<BusiReportWrgEntity>()
        );

        return new PageUtils(page);
    }

}