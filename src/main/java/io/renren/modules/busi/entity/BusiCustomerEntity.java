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
 * @date 2020-11-22 23:09:03
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

}
