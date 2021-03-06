package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
	 * 意向项目名称
	 */
	@TableField(exist = false)
	private String projectName;

	/**
	 * 意向项目名称
	 */
	@TableField(exist = false)
	private String projectNames;

	/**
	 * 经纪人id
	 */
	private String userId;

	/**
	 * 经纪人name
	 */
	private String userName;

	/**
	 * 置业顾问id
	 */
	private String matchUserId;

	/**
	 * 置业顾问name
	 */
	private String matchUserName;

	/**
	 * 中介销售姓名
	 */
	@TableField(exist = false)
	private String middleName;

	/**
	 * 经济人电话
	 */
	@TableField(exist = false)
	private String middleUserMobile;
	/**
	 * 渠道id
	 */
	@TableField(exist = false)
	private long middleTypeId;
	/**
	 * 渠道名称
	 */
	@TableField(exist = false)
	private String middleTypeName;

	/**
	 * 关联客户记录id
	 */
	private Integer customerId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 状态更新时间
	 */
	private Date statusUpdatedTime;

	/**
	 * 业务状态
	 */
	@TableField(exist = false)
	private Integer busiStatus;

	/**
	 * 业务状态更新时间
	 */
	@TableField(exist = false)
	private Date busiStatusUpdatedTime;

	/**
	 * 报备时间
	 */
	private Date createdTime;


	/**
	 * 最近报备时间，可以重复报备，录入记录时默认为created_time
	 */
	private Date updatedTime;

	/**
	 * 保护期截至日期
	 */
	private Date expired;

	/**
	 * 拒收原因
	 */
	private String reason;

	/**
	 * 分配人
	 */
	@TableField(exist = false)
	private String assigner;
}
