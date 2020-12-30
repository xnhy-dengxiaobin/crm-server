package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.busi.entity.BusiOrderEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-29 00:08:46
 */
@Mapper
public interface BusiOrderDao extends BaseMapper<BusiOrderEntity> {
    List<BusiOrderEntity> listPage(Map<String, Object> params);
    Integer listPageCount(Map<String, Object> params);
}
