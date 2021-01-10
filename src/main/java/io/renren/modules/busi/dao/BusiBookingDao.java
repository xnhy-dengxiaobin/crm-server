package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.BusiBookingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 认筹表
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-04 16:42:11
 */
@Mapper
public interface BusiBookingDao extends BaseMapper<BusiBookingEntity> {
	List<Map<String,Object>> renchouGroup(Map param);
}
