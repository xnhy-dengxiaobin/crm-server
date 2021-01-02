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

import io.renren.modules.busi.dao.MiddleTypeDao;
import io.renren.modules.busi.entity.MiddleTypeEntity;
import io.renren.modules.busi.service.MiddleTypeService;


@Service("middleTypeService")
public class MiddleTypeServiceImpl extends ServiceImpl<MiddleTypeDao, MiddleTypeEntity> implements MiddleTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
        long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long

        List<MiddleTypeEntity> slct = getBaseMapper().slct(params);
        Integer cnt = getBaseMapper().cnt(params);
        Page<MiddleTypeEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal(cnt);
        page.setRecords(slct);

        return new PageUtils(page);
    }

    @Override
    public List<MiddleTypeEntity> qryLst(Map<String, Object> params) {
        long parentId = ParamResolvor.getLongAsDefault(params,"parentId",0);
        if(parentId > 0) {
            return getBaseMapper().mdchild(params);
        }else{
            return getBaseMapper().mdparent(params);
        }
    }
    @Override
    public List<MiddleTypeEntity> qryWxList(Map<String, Object> params) {
        long parentId = ParamResolvor.getLongAsDefault(params,"parentId",0);
        if(parentId > 0) {
            return getBaseMapper().wxmdchild(params);
        }else{
            return getBaseMapper().wxmdparent(params);
        }
    }
}