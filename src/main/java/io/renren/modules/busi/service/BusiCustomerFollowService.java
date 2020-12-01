package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerFollowEntity;

import java.util.Map;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
public interface BusiCustomerFollowService extends IService<BusiCustomerFollowEntity> {

    PageUtils queryPage(Map<String, Object> params);
    Integer toDayCount(String projectId);
}

