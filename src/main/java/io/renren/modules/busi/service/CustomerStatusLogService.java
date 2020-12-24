package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.CustomerStatusLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-22 22:29:25
 */
public interface CustomerStatusLogService extends IService<CustomerStatusLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CustomerStatusLogEntity> queryList(Map<String, Object> params);
}

