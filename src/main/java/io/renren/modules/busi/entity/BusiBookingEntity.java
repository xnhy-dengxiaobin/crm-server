package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 认筹表

 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-05 21:24:01
 */
@Data
@TableName("busi_booking")
public class BusiBookingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 认筹guid
	 */
	@TableId
	private String bookingguid;
	/**
	 *
	 */
	private String buguid;
	/**
	 * 项目guid
	 */
	private String projguid;
	/**
	 * 机会guid
	 */
	private String oppguid;
	/**
	 * 认筹开始日期
	 */
	private String bgndate;
	/**
	 * 认筹结束日期
	 */
	private String enddate;
	/**
	 * 排号
	 */
	private String projnum;
	/**
	 *
	 */
	private String roomnum;
	/**
	 * 币种
	 */
	private String bz;
	/**
	 * 总房款
	 */
	private String total;
	/**
	 * 置业顾问guid
	 */
	private String userguid;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 关闭日期
	 */
	private String closedate;
	/**
	 * 关闭原因
	 */
	private String reason;
	/**
	 * 房间guid
	 */
	private String roomguid;
	/**
	 * 关闭原因，好像重复了
	 */
	private String closeyy;
	/**
	 *
	 */
	private String sourceguid;
	/**
	 * 创建人
	 */
	private String createdby;
	/**
	 * 创建人guid
	 */
	private String createdbyguid;
	/**
	 * 创建日期
	 */
	private String createdon;
	/**
	 *
	 */
	private String iscreatoruse;
	/**
	 * 客户
	 */
	private String cstallguid;
	/**
	 * 客户名字
	 */
	private String cstname;
	/**
	 *
	 */
	private String cstguid1;
	/**
	 *
	 */
	private String cstguid2;
	/**
	 *
	 */
	private String cstguid3;
	/**
	 *
	 */
	private String cstguid4;
	/**
	 *
	 */
	private String roominfo;
	/**
	 *
	 */
	private String bldguid;
	/**
	 * 业务员
	 */
	private String ywy;
	/**
	 *
	 */
	private String bldarea;
	/**
	 *
	 */
	private String tnarea;
	/**
	 * 实收金额
	 */
	private String ssamount;

}
