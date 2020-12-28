package io.renren.modules.busi.dao;

import io.renren.modules.busi.entity.BusiCustomerFollowEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@Mapper
public interface BusiCustomerFollowDao extends BaseMapper<BusiCustomerFollowEntity> {
  Integer toDayCount(String projectId, LocalDate localDate);
  List<BusiCustomerFollowEntity> listPage(Map<String, Object> params);

  Integer listPageCount(Map<String, Object> params);
}
