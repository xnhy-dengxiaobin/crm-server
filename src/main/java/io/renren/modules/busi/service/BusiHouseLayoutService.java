package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.Map;

/**
 * 项目户型图
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-26 18:29:59
 */
public interface BusiHouseLayoutService extends IService<BusiHouseLayoutEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

