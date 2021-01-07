package io.renren.modules.erp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 未认购
 * 
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-06 17:24:58
 */
@Data
@TableName("busi_report_wrg")
public class BusiReportWrgEntity implements Serializable {
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
	 * 客户名字
	 */
	private String cstname;
	/**
	 * 客户电话
	 */
	private String tel;
	/**
	 * 起始时间
	 */
	private Date qstime;
	/**
	 * 
	 */
	private Date sxdate;
	/**
	 * 
	 */
	private Integer num;
	/**
	 * 应收
	 */
	private BigDecimal ystotal;
	/**
	 * 实收
	 */
	private BigDecimal sstotal;
	/**
	 * 逾期天数，小于0表示3日内签约的数据
	 */
	private Integer yqdate;
	/**
	 * 销售状态
	 */
	private String salestatus;
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
	private String roomguid;
	/**
	 * 
	 */
	private Date qsdate;
	/**
	 * 
	 */
	private String saleguid;
	/**
	 * 
	 */
	private String oppguid;
	/**
	 * 
	 */
	private Date syncTime;

}
