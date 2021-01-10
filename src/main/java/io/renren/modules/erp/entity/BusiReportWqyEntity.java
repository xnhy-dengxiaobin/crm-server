package io.renren.modules.erp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-06 17:24:57
 */
@Data
@TableName("busi_report_wqy")
public class BusiReportWqyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 房间信息
	 */
	private String roominfo;
	/**
	 * 客户姓名
	 */
	private String cstname;
	/**
	 * 客户电话
	 */
	private String tel;
	/**
	 * 认购日期
	 */
	private Date rgdate;
	/**
	 * 逾期天数
	 */
	private Integer yqdate;
	/**
	 * 总价
	 */
	private BigDecimal rmbcjtotal;
	/**
	 * 订单类型
	 */
	private String ordertype;
	/**
	 * 置业顾问
	 */
	private String username;
	/**
	 * 
	 */
	private String tradeguid;
	/**
	 * 
	 */
	private String projguid;
	/**
	 * 
	 */
	private Date qsdate;
	/**
	 * 
	 */
	private String orderguid;
	/**
	 * 
	 */
	private String oppguid;
	/**
	 * 
	 */
	private Date syncTime;

}
