package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiHouseGroupEntity;

import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-26 23:05:13
 */
public interface BusiHouseGroupService extends IService<BusiHouseGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

