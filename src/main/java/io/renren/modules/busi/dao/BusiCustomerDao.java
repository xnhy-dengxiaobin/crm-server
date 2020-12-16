package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import org.apache.ibatis.annotations.Mapper;

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
  IPage<BusiCustomerEntity> publicPage(IPage page, String projectId,String keywords, Integer stt);
    List<BusiCustomerEntity> selectByPhone(String phone);
  long countNormal(Object projectId);
  long countTimeout(Object projectId);


  List<Map> groupByDateCountDay(String endDate, Integer projectId);
  List<Map> groupByDateCountWeek(String endDate, Integer projectId);
  List<Map> groupByDateCountMonth(String endDate, Integer projectId);
  List<Map> groupByDateCountYear(String endDate, Integer projectId);

}
