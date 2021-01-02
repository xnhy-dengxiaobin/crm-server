package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.BusiCustomerFollowEntity;

import java.util.List;
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

    /**
     * 跟进信息
     * 更新客户状态
     * 插入跟进信息
     * @param busiCustomerFollow
     */
    void saveFollow(BusiCustomerFollowEntity busiCustomerFollow, BusiCustomerEntity busiCustomerEntity);


    PageUtils listPage(Map<String, Object> params, List<Integer> projectIds);
}

