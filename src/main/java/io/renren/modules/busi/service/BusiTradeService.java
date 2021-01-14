package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiTradeEntity;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-04 16:42:10
 */
public interface BusiTradeService extends IService<BusiTradeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Long rengouCount(Map param);

    Long qianyueCount(Map param);

    List<Map> groupByDateCount(String endDate, String [] projectIds, String type);

    List<Map> qianyueGroupByDateCount(String endDate,String [] projectIds,String type);
}

