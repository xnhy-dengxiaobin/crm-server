package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.ReceptionDao;
import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.service.ReceptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("receptionService")
public class ReceptionServiceImpl extends ServiceImpl<ReceptionDao, ReceptionEntity> implements ReceptionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<ReceptionEntity> receptionEntityQueryWrapper = new QueryWrapper<>();
        if(params.get("salerId") != null){
            receptionEntityQueryWrapper.eq("saler_id",params.get("salerId"));
        }
        IPage<ReceptionEntity> page = this.page(
                new Query<ReceptionEntity>().getPage(params),
                receptionEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listBySalerId(Map<String, Object> params, Long salerId){
        IPage<ReceptionEntity> page = this.page(
                new Query<ReceptionEntity>().getPage(params)
        );
        IPage<Map<String, Object>> mapIPage = getBaseMapper().listBySalerId(page, salerId);
        return new PageUtils(mapIPage);
    }


}
