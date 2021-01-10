package io.renren.modules.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.erp.entity.BusiReportWjkEntity;

import java.util.Map;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-06 17:24:58
 */
public interface BusiReportWjkService extends IService<BusiReportWjkEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listYqWjk(Map<String, Object> params);
}

