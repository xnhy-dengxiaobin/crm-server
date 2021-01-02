package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.busi.entity.MiddleTypeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 中介来源，类型
 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-14 22:01:22
 */
@Mapper
public interface MiddleTypeDao extends BaseMapper<MiddleTypeEntity> {

    List<MiddleTypeEntity> slct(Map<String, Object> params);

    Integer cnt(Map<String, Object> params);

    List<MiddleTypeEntity> mdparent(Map<String, Object> params);

    List<MiddleTypeEntity> mdchild(Map<String, Object> params);

    List<MiddleTypeEntity> wxmdparent(Map<String, Object> params);

    List<MiddleTypeEntity> wxmdchild(Map<String, Object> params);
}
