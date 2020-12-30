package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-29 00:08:46
 */
@Data
@TableName("busi_order")
public class BusiOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 房间id room_id
	 */
	private Integer roomId;
	/**
	 * 建筑面积 bldarea
	 */
	private BigDecimal floorArea;
	/**
	 * 套内面积 tnarea
	 */
	private BigDecimal insideFloorArea;
	/**
	 * 建筑单价 price
	 */
	private BigDecimal floorAreaPrice;
	/**
	 * 套内单价 tnprice
	 */
	private BigDecimal insideFloorAreaPrice;
	/**
	 * 标准总价 total
	 */
	private BigDecimal totalPrices;
	/**
	 * 计价模式：按建筑面积，按套内面积
	 */
	private String calMode;
	/**
	 * 付款方式：
一次性付款
商业贷款
公积金付款
公积金贷款
组合贷款
按揭付款
按揭贷款
分期付款
分期付款（半年）
分期付款（全年）


	 */
	private String payForm;
	/**
	 * 折扣
	 */
	private String discount;
	/**
	 * 折扣后建筑单价 price
	 */
	private BigDecimal floorAreaPriceDs;
	/**
	 * 折扣后套内单价 tnprice
	 */
	private BigDecimal insideFloorAreaPriceDs;
	/**
	 * 折扣后总价 total
	 */
	private BigDecimal totalPricesDs;
	/**
	 * 按揭银行 ajbank
	 */
	private String mortgateBank;
	/**
	 * 按揭金额 ajtotal
	 */
	private BigDecimal mortgateTotal;
	/**
	 * 按揭年限 ajyear
	 */
	private Integer mortgateYear;
	/**
	 * 公积金银行
	 */
	private String hfBank;
	/**
	 * 公积金总额
	 */
	private BigDecimal hfTotal;
	/**
	 * 公积金年限
	 */
	private Integer hfYear;
	/**
	 * 是否有效
	 */
	private Integer isValid;
	/**
	 * 订单类型 ordertype 认购
小订
预留
	 */
	private String type;
	/**
	 * 房间guid
	 */
	private String roomGuid;
	/**
	 * 生成时间
	 */
	private Date createdTime;
	/**
	 * 订单guid
	 */
	private String guid;
	/**
	 * 0：资料已收齐，1：资料未收齐
	 */
	private Integer dataPrepared;

  /**
   * 对应的项目ID
   */
	private Integer projectId;

	@TableField(exist=false)
	private String customerInfo;

	@TableField(exist=false)
	private String name;

}
