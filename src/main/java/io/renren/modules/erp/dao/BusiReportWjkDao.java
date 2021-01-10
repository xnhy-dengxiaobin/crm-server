package io.renren.modules.erp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.erp.entity.BusiReportWjkEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-06 17:24:58
 */
@Mapper
public interface BusiReportWjkDao extends BaseMapper<BusiReportWjkEntity> {

    List<BusiReportWjkEntity> listYqWjk(Map<String, Object> params);

    Long listYqWjkCount(Map<String, Object> params);
}
