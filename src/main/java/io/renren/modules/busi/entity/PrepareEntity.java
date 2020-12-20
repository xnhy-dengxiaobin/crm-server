package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * WX报备表
 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-20 11:18:56
 */
@Data
@TableName("busi_prepare")
public class PrepareEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Integer id;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 手机1
	 */
	private String mobile1;
	/**
	 * 手机2
	 */
	private String mobile2;
	/**
	 * 手机3
	 */
	private String mobile3;
	/**
	 * 意向楼ID
	 */
	private Integer projectId;
	/**
	 * 中介销售ID
	 */
	private String userId;
	/**
	 * 关联客户记录id
	 */
	private String customerId;
	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 报备时间
	 */
	private Date createdTime;

}
