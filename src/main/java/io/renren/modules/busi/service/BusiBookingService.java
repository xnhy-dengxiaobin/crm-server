package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiBookingEntity;

import java.util.List;
import java.util.Map;

/**
 * 认筹表
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-04 16:42:11
 */
public interface BusiBookingService extends IService<BusiBookingEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Map<String,Object>> renchouGroup(Map param);
}

