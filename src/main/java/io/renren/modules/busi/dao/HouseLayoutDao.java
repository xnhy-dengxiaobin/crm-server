package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.busi.entity.HouseLayoutEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目户型图
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-28 10:32:11
 */
@Mapper
public interface HouseLayoutDao extends BaseMapper<HouseLayoutEntity> {
    List<HouseLayoutEntity> selectList4Project(Map<String, Object> params);
    long selectList4ProjectCount(Map<String, Object> params);
}
