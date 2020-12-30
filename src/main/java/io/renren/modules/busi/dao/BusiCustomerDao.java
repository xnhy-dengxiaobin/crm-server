package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@Mapper
public interface BusiCustomerDao extends BaseMapper<BusiCustomerEntity> {
  IPage<BusiCustomerEntity> normalFollowPage(IPage page, String userId,String projectId);
  IPage<BusiCustomerEntity> timeoutPage(IPage page, String userId,String projectId,String keywords);
  IPage<BusiCustomerEntity> publicPage(IPage page, String projectId,String keywords, Integer stt, Long matchUserId);
    List<BusiCustomerEntity> selectByPhone(String phone);
  long countNormal(Object projectId);
  long countTimeout(Object projectId);
  long countRepetition(Object projectId);
  long countCollide(Object projectId);

  List<Map> groupByDateCountDay(String endDate, String[] projectIds);
  List<Map> groupByDateCountWeek(String endDate, String[] projectIds);
  List<Map> groupByDateCountMonth(String endDate, String[] projectIds);
  List<Map> groupByDateCountYear(String endDate, String[] projectIds);
  List<Map> groupRepetition(Map<String, Object> params);
  List<Map> collideList(Map<String, Object> params);



  @Select("select * from busi_customer where source='来电' and not exists(select 1 from busi_reception where customer_id = busi_customer.id) and date_add(create_time,interval 2 day) < now()")
  List<BusiCustomerEntity> queryCallVisit();
}
