package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-22 22:29:25
 */
@Data
@TableName("busi_customer_status_log")
public class CustomerStatusLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 报备id
	 */
	private Integer preparedId;
	/**
	 * 客户id
	 */
	private Integer customerId;
	/**
	 * 状态：中文
	 */
	private String status;
	/**
	 * 动作
	 */
	private String action;
	/**
	 * 说明1
	 */
	private String memo1;
	/**
	 * 说明2
	 */
	private String memo2;
	/**
	 * 说明3
	 */
	private String memo3;
	/**
	 * 说明4
	 */
	private String memo4;
	/**
	 * 说明5
	 */
	private String memo5;
	/**
	 * 日志生成时间
	 */
	private Date foundTime;
	/**
	 * 操作人id
	 */
	private Integer userId;
	/**
	 * 操作人姓名
	 */
	private String userName;

}
