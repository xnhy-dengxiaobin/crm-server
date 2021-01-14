package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerBookingEntity;

import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-04 16:42:11
 */
public interface BusiCustomerBookingService extends IService<BusiCustomerBookingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

