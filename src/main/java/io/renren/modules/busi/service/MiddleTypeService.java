package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.MiddleTypeEntity;

import java.util.Map;

/**
 * 中介来源，类型
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-14 22:01:22
 */
public interface MiddleTypeService extends IService<MiddleTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

