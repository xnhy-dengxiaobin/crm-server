package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiPressLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-13 17:34:09
 */
public interface BusiPressLogService extends IService<BusiPressLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

