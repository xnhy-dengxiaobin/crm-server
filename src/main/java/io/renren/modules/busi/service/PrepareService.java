package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.PrepareCheckEntity;
import io.renren.modules.busi.entity.PrepareEntity;

import java.util.Map;

/**
 * WX报备表
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-20 11:18:56
 */
public interface PrepareService extends IService<PrepareEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils lstPage(Map<String, Object> params);

    boolean check(PrepareCheckEntity checks);

}

