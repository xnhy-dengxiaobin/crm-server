package io.renren.modules.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.erp.entity.BusiReportWrgEntity;

import java.util.Map;

/**
 * 未认购
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-06 17:24:58
 */
public interface BusiReportWrgService extends IService<BusiReportWrgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

