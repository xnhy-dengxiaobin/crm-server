package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.BusiTradeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-04 16:42:10
 */
@Mapper
public interface BusiTradeDao extends BaseMapper<BusiTradeEntity> {
	Long rengouCount(Map param);

	Long qianyueCount(Map param);

	List<Map> groupByDateCount(String endDate,String [] projectIds,String type);
	List<Map> qianyueGroupByDateCount(String endDate,String [] projectIds,String type);

}
