package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.PrepareEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * WX报备表
 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-20 11:18:56
 */
@Mapper
public interface PrepareDao extends BaseMapper<PrepareEntity> {
    List<PrepareEntity> slct(Map<String, Object> params);
    long cnt(Map<String, Object> params);

    PrepareEntity gtById(Integer userId, Integer id);
}
