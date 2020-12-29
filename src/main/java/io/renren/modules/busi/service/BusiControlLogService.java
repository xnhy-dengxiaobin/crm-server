package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiControlLogEntity;

import java.util.Map;

/**
 * 销控日志
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-27 13:25:39
 */
public interface BusiControlLogService extends IService<BusiControlLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

