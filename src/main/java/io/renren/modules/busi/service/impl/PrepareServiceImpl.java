package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.ParamResolvor;
import io.renren.modules.busi.entity.PrepareCheckEntity;
import io.renren.modules.busi.entity.ReceptionEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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
import org.springframework.transaction.annotation.Transactional;


@Service("prepareService")
public class PrepareServiceImpl extends ServiceImpl<PrepareDao, PrepareEntity> implements PrepareService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String mobile = (String)params.get("mobile");
        IPage<PrepareEntity> page = this.page(
                new Query<PrepareEntity>().getPage(params),
                new QueryWrapper<PrepareEntity>().like(StringUtils.isNotBlank(mobile),"mobile", mobile)
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean check(PrepareCheckEntity checks) {
        for(Integer id:checks.getIds()){
             PrepareEntity pe= new PrepareEntity();
             pe.setId(id);
             pe.setStatus(checks.getStatus());
             pe.setUpdatedTime(new Date());
             getBaseMapper().updateById(pe);
        }
        return true;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String wxSave(PrepareEntity prepare,long userId) {
        String msg="";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile",prepare.getMobile());
        params.put("user_id",userId);
        List<PrepareEntity> pes = getBaseMapper().selectList(new QueryWrapper<PrepareEntity>()
                .eq("user_id",userId).eq("mobile",prepare.getMobile()));
        List<PrepareEntity> peso = getBaseMapper().selectList(new QueryWrapper<PrepareEntity>()
                .ne("user_id",userId).eq("mobile",prepare.getMobile()).ge("status",0));
        if(pes.size() > 0 && peso.size() == 0) {
            if(pes.get(0).getStatus() >=0) {
                prepare.setId(pes.get(0).getId());
                prepare.setUpdatedTime(new Date());
                getBaseMapper().updateById(prepare);
                msg = "已重新报备";
            }else{
                prepare.setUpdatedTime(new Date());
                prepare.setStatusUpdatedTime(new Date());
                prepare.setStatus(0);
                prepare.setId(pes.get(0).getId());
                getBaseMapper().updateById(prepare);
                msg = "已重新报备";
            }
        }else if(peso.size() > 0 && pes.size() == 0) {
            prepare.setStatus(-10);
            prepare.setCreatedTime(new Date());
            prepare.setStatusUpdatedTime(new Date());
            prepare.setUpdatedTime(new Date());
            getBaseMapper().insert(prepare);
            msg="手机号拒收无效";
        }else if(peso.size() > 0 && pes.size() > 0) {
            msg="手机号拒收无效";
        }else {
            prepare.setCreatedTime(new Date());
            prepare.setUpdatedTime(new Date());
            prepare.setStatusUpdatedTime(new Date());
            getBaseMapper().insert(prepare);
            msg="报备成功";
        }
        return msg;
    }

    @Override
    public PrepareEntity gtById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getBaseMapper().gtById(null, id);
    }
}