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
 * @date 2020-11-28 11:49:14
 */
@Data
@TableName("busi_customer_project")
public class BusiCustomerProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增
	 */
	@TableId
	private Integer id;
	/**
	 * 意向产品
	 */
	private String project;
	/**
	 * 意向产品id,关联busi_project
	 */
	private Integer projectId;
	/**
	 * 客户id
	 */
	private Integer customerId;
	/**
	 * 状态  status  1:来访2：认筹3:认购4:签约
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
