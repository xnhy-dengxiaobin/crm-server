package io.renren.modules.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.modules.erp.dao.BusiReportWjkDao;
import io.renren.modules.erp.entity.BusiReportWjkEntity;
import io.renren.modules.erp.service.BusiReportWjkService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busiReportWjkService")
public class BusiReportWjkServiceImpl extends ServiceImpl<BusiReportWjkDao, BusiReportWjkEntity> implements BusiReportWjkService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiReportWjkEntity> page = this.page(
                new Query<BusiReportWjkEntity>().getPage(params),
                new QueryWrapper<BusiReportWjkEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listYqWjk(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long


        List<BusiReportWjkEntity> list = getBaseMapper().listYqWjk(params);
        Long cnt = getBaseMapper().listYqWjkCount(params);
        Page<BusiReportWjkEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(list);

        return new PageUtils(page);
    }

}
