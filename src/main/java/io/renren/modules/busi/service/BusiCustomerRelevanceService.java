package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerRelevanceEntity;

import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-28 11:49:13
 */
public interface BusiCustomerRelevanceService extends IService<BusiCustomerRelevanceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

