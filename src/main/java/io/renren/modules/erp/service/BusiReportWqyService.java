package io.renren.modules.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.erp.entity.BusiReportWqyEntity;

import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-06 17:24:57
 */
public interface BusiReportWqyService extends IService<BusiReportWqyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

