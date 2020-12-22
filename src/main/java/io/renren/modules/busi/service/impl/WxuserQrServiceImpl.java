package io.renren.modules.busi.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.WxuserQrDao;
import io.renren.modules.busi.entity.WxuserQrEntity;
import io.renren.modules.busi.service.WxuserQrService;


@Service("wxuserQrService")
public class WxuserQrServiceImpl extends ServiceImpl<WxuserQrDao, WxuserQrEntity> implements WxuserQrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WxuserQrEntity> page = this.page(
                new Query<WxuserQrEntity>().getPage(params),
                new QueryWrapper<WxuserQrEntity>()
        );

        return new PageUtils(page);
    }

}