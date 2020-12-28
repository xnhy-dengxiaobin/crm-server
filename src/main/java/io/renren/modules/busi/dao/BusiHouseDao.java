package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.BusiHouseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 房源
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@Mapper
public interface BusiHouseDao extends BaseMapper<BusiHouseEntity> {
    List<BusiHouseEntity> listPage(Map<String, Object> params);

    Long listPageCount(Map<String, Object> params);
}
