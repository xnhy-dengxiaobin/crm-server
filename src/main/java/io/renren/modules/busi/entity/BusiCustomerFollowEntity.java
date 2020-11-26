package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@Data
@TableName("busi_customer_follow")
public class BusiCustomerFollowEntity implements Serializable {
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
	 * 跟进方式
	 */
	private String mode;
	/**
	 * 意向级别
	 */
	private String grade;
	/**
	 * 跟进内容
	 */
	private String content;
	/**
	 * 下次跟进时间
	 */
	private Date nextDate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 跟进内容
	 */
	private String createName;

}
