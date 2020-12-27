package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerEntity;

import java.util.List;
import java.util.Map;

/**
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
public interface BusiCustomerService extends IService<BusiCustomerEntity> {

  PageUtils queryPage(Map<String, Object> params);

  IPage<BusiCustomerEntity> normalFollowPage(IPage<BusiCustomerEntity> page, String userId, String projectId);

  IPage<BusiCustomerEntity> timeoutPage(IPage<BusiCustomerEntity> page, String userId, String projectId, String keywords);

  IPage<BusiCustomerEntity> publicPage(IPage<BusiCustomerEntity> page, String projectId, String keywords, Integer stt, Long matchUserId);

  /**
   * 根据项目ID 统计正常跟进数量
   *
   * @param projectId
   * @return
   */
  long countNormal(Object projectId);

  /**
   * 根据项目ID 统计超时跟进数量
   *
   * @param projectId
   * @return
   */
  long countTimeout(Object projectId);

  /**
   * 根据完整电话号码或者后四位查询
   *
   * @param params
   * @return
   */
  List<BusiCustomerEntity> queryByPhone(Map<String, Object> params);

  /**
   * 查询 2天没有到访的来电客户
   */
  List<BusiCustomerEntity> queryCallVisit();

  /**
   * 完善客户信息
   * 将接待记录置为已处理
   *
   * @param busiCustomerEntity
   */
  void perfect(BusiCustomerEntity busiCustomerEntity);

  List<Map> groupByDateCountDay(String endDate, Integer projectId);

  List<Map> groupByDateCountWeek(String endDate, Integer projectId);

  List<Map> groupByDateCountMonth(String endDate, Integer projectId);

  List<Map> groupByDateCountYear(String endDate, Integer projectId);

  void recovery(String[] ids,String userName);
}

