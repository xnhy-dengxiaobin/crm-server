package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.dao.HouseLayoutDao;
import io.renren.modules.busi.entity.HouseLayoutEntity;
import io.renren.modules.busi.service.HouseLayoutService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("houseLayoutService")
public class HouseLayoutServiceImpl extends ServiceImpl<HouseLayoutDao, HouseLayoutEntity> implements HouseLayoutService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        /*IPage<HouseLayoutEntity> page = this.page(
                new Query<HouseLayoutEntity>().getPage(params),
                new QueryWrapper<HouseLayoutEntity>()
        );*/

        long currentPage = Long.valueOf(params.getOrDefault("page", "0").toString());
        long limit = Long.valueOf(params.getOrDefault("limit", "").toString());
        long offset = (currentPage - 1) * limit;
        params.put("offset", offset);
        params.put("limit", limit); //将string转为long

        List<HouseLayoutEntity> houseLayoutEntities = getBaseMapper().selectList4Project(params);
        long count = getBaseMapper().selectList4ProjectCount(params);
        Page<HouseLayoutEntity> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        page.setTotal((Long) count);
        page.setRecords(houseLayoutEntities);

        return new PageUtils(page);
    }

    @Override
    public HouseLayoutEntity getById4Project(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("offset", 0);
        params.put("limit", 1);
        List<HouseLayoutEntity> houseLayoutEntities = getBaseMapper().selectList4Project(params);
        return CollectionUtils.isEmpty(houseLayoutEntities) ? null : houseLayoutEntities.get(0);
    }
}