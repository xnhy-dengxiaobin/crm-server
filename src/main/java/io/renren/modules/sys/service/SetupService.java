package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SetupEntity;

import java.util.Map;

/**
 * 
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-19 23:12:44
 */
public interface SetupService extends IService<SetupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

