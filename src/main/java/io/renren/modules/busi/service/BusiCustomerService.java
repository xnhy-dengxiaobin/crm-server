package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerEntity;

import java.util.List;
import java.util.Map;

/**
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
public interface BusiCustomerService extends IService<BusiCustomerEntity> {

  PageUtils queryPage(Map<String, Object> params);

  IPage<BusiCustomerEntity> normalFollowPage(IPage<BusiCustomerEntity> page, String userId);

    /**
     * 根据完整电话号码或者后四位查询
     * @param params
     * @return
     */
    List<BusiCustomerEntity> queryByPhone(Map<String, Object> params);
}

