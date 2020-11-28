package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerProjectEntity;

import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-28 11:49:14
 */
public interface BusiCustomerProjectService extends IService<BusiCustomerProjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

