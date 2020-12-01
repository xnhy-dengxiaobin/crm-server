package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerRoamEntity;

import java.util.Map;

/**
 * 转介路径
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-01 23:36:55
 */
public interface BusiCustomerRoamService extends IService<BusiCustomerRoamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

