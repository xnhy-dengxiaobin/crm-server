package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-29 00:08:46
 */
@Data
@TableName("busi_customer_order")
public class BusiCustomerOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 客户id
	 */
	private Integer customerId;
	/**
	 * 订单id
	 */
	private Integer orderId;
	/**
	 * 客户guid
	 */
	private String customerGuid;
	/**
	 * 订单guid
	 */
	private String orderGuid;

}
