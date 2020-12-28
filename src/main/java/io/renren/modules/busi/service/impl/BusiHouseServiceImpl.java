package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiHouseDao;
import io.renren.modules.busi.entity.BusiHouseEntity;
import io.renren.modules.busi.service.BusiHouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busiHouseService")
public class BusiHouseServiceImpl extends ServiceImpl<BusiHouseDao, BusiHouseEntity> implements BusiHouseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusiHouseEntity> page = this.page(
                new Query<BusiHouseEntity>().getPage(params),
                new QueryWrapper<BusiHouseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listPage(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long
        List<BusiHouseEntity> list = getBaseMapper().listPage(params);
        Long cnt = getBaseMapper().listPageCount(params);
        Page<BusiHouseEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
