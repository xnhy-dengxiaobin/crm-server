package io.renren.modules.busi.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.busi.dao.SignOnDao;
import io.renren.modules.busi.entity.SignOnEntity;
import io.renren.modules.busi.service.SignOnService;


@Service("signOnService")
public class SignOnServiceImpl extends ServiceImpl<SignOnDao, SignOnEntity> implements SignOnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SignOnEntity> page = this.page(
                new Query<SignOnEntity>().getPage(params),
                new QueryWrapper<SignOnEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public boolean isSignOn(Integer userId) {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        List<SignOnEntity> signOns = getBaseMapper().selectByMap(new HashMap<String, Object>(){{
            put("user_id", userId);
            put("sign_on_date", today);
        }});

        if (CollectionUtils.isNotEmpty(signOns)) {
            return true;
        }
        return false;
    }
}