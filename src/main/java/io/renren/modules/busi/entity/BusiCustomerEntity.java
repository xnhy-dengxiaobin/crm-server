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
 * @date 2020-11-25 16:23:57
 */
@Data
@TableName("busi_customer")
public class BusiCustomerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增
	 */
	@TableId
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 手机号
	 */
	private String mobilePhone;
	/**
	 * 意向项目,关联busi_project
	 */
	private Integer projectId;
	/**
	 * 购房用途
	 */
	private String purpose;
	/**
	 * 意向产品
	 */
	private String purposeProduct;
	/**
	 * 意向面积
	 */
	private String purposeArea;
	/**
	 * 意向户型
	 */
	private String purposeType;
	/**
	 * 意向朝向
	 */
	private String purposeOrientation;
	/**
	 * 意向楼层
	 */
	private String purposeFloor;
	/**
	 * 客户分级
	 */
	private String grade;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * 职业
	 */
	private String profession;
	/**
	 * 购房次数
	 */
	private Integer quantity;
	/**
	 * 关注
	 */
	private String follow;
	/**
	 * 分配的职业顾问
	 */
	private String matchUserId;
	/**
	 * 最后跟进文本
	 */
	private String followLast;

	private Date followNextDate;

	private Date followDate;


	/**
	 * 认知途径
	 */
	private String channel;
	/**
	 * 来访次数
	 */
	private Integer comeCount;
	/**
	 * 客户来源
	 */
	private String source;
	/**
	 * 跟进方式
	 */
	private String followMode;
	/**
	 * 家庭结构
	 */
	private String familyStructure;
	/**
	 * 证件类型
	 */
	private String idType;
	/**
	 * 证件号码
	 */
	private String idNumber;
	/**
	 * 通讯地址
	 */
	private String address;
	/**
	 * 竞品对比
	 */
	private String situation;
	/**
	 * 状态：1：新客户2：来访3：
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
  /**
   * 原来的置业顾问
   */
  private String oldMatchUserId;
  /**
   * 原来的置业顾问姓名
   */
  private String oldMatchUserName;
}
