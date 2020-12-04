package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.SignOnEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工签到表

 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-04 20:26:45
 */
@Mapper
public interface SignOnDao extends BaseMapper<SignOnEntity> {
	
}
