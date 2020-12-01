package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 转介路径
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-01 23:36:55
 */
@Data
@TableName("busi_customer_roam")
public class BusiCustomerRoamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增长id
	 */
	@TableId
	private Integer id;
	/**
	 * 客户ID
	 */
	private Integer customerId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 转介原因
	 */
	private String remark;
	/**
	 * 转到的置业顾问
	 */
	private Integer userId;

}
