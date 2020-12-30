package io.renren.modules.busi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.busi.entity.BusiOrderEntity;
import io.renren.modules.busi.vo.BusiOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-29 00:08:46
 */
@Mapper
public interface BusiOrderDao extends BaseMapper<BusiOrderEntity> {
  List<BusiOrderEntity> listPage(Map<String, Object> params);

  Integer listPageCount(Map<String, Object> params);

  @Select("<script>select * from (\n" +
    "select t1.id orderId,t1.floor_area,t1.total_prices_ds,t1.pay_form,t1.type,GROUP_CONCAT(CONCAT(t3.name,\"【\",t3.sex,\"】\",t3.mobile_phone)) customerInfo from busi_order t1 inner join busi_customer_order t2 on (t1.id=t2.order_id) inner join busi_customer t3 on (t2.customer_id=t3.id) inner join busi_project t4 on(t4.id = t1.project_id) where t1.data_prepared=1 \n" +
    "group by t1.id \n" +
    ")t where 1=1 \n" +
    "  and customerInfo LIKE CONCAT('%',#{condition},'%')\n" +
    "</script>")
  IPage<BusiOrderVO> promptPage(IPage iPage,String condition);
}
