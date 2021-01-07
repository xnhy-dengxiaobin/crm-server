package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiTradeDao;
import io.renren.modules.busi.entity.BusiTradeEntity;
import io.renren.modules.busi.service.BusiTradeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busiTradeService")
public class BusiTradeServiceImpl extends ServiceImpl<BusiTradeDao, BusiTradeEntity> implements BusiTradeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BusiTradeEntity> wrapper = new QueryWrapper<>();
        if(params.get("roomId") != null){
            wrapper.lambda().eq(BusiTradeEntity::getRoomId,params.get("roomId"));
        }
        if(params.get("roomStatus") != null){
            wrapper.lambda().eq(BusiTradeEntity::getRoomStatus,params.get("roomStatus"));
        }
        IPage<BusiTradeEntity> page = this.page(
                new Query<BusiTradeEntity>().getPage(params),wrapper
        );

        return new PageUtils(page);
    }

}