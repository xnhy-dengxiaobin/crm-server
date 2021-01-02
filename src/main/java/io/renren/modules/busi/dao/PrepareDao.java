package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.PrepareEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.busi.entity.ReceptionEntity;
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
    List<PrepareEntity> selectCheckList(Map<String, Object> params);
    long cnt(Map<String, Object> params);
    Long checkCnt(Map<String, Object> params);

    PrepareEntity gtById(Integer userId, Integer id);

    List<PrepareEntity> selectPage4Admin(Map<String, Object> params);
    long count4Admin(Map<String, Object> params);
}
