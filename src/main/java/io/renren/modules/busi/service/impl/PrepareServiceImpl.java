package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.ParamResolvor;
import io.renren.modules.busi.entity.ReceptionEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.PrepareDao;
import io.renren.modules.busi.entity.PrepareEntity;
import io.renren.modules.busi.service.PrepareService;


@Service("prepareService")
public class PrepareServiceImpl extends ServiceImpl<PrepareDao, PrepareEntity> implements PrepareService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PrepareEntity> page = this.page(
                new Query<PrepareEntity>().getPage(params),
                new QueryWrapper<PrepareEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils lstPage(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long

        List<PrepareEntity> list = getBaseMapper().slct(params);
        long cnt = getBaseMapper().cnt(params);

        Page<PrepareEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(list);

        return new PageUtils(page);
    }
}