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
 * @date 2021-01-06 17:24:58
 */
@Data
@TableName("busi_report_wjk")
public class BusiReportWjkEntity implements Serializable {
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
	 * 起始时间
	 */
	private Date qstime;
	/**
	 * 逾期天数，小于0表示7日到期
	 */
	private Integer yqdate;
	/**
	 * 房款总额
	 */
	private BigDecimal rmbtotal;
	/**
	 * 欠款金额
	 */
	private BigDecimal qktotal;
	/**
	 * 销售状态
	 */
	private String salestatus;
	/**
	 * 置业顾问姓名
	 */
	private String username;
	/**
	 * 项目guid
	 */
	private String projguid;
	/**
	 * 起始时间，有点重复
	 */
	private Date qsdate;
	/**
	 * 房间guid
	 */
	private String roomguid;
	/**
	 * 交易guid
	 */
	private String tradeguid;
	/**
	 * 认购是bookingguid，签约是tradeguid，签合同是contractguid
	 */
	private String saleguid;
	/**
	 * 1-未放贷；2-认购未交定金；3-签约未交定金；4-认购未交房款；5-签约未交房款；6-7日内到期
	 */
	private Integer type;
	/**
	 * 
	 */
	private Date syncTime;

}
