package io.renren.modules.busi.entity;

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
 * @date 2021-01-14 14:29:24
 */
@Data
@TableName("busi_contract")
public class BusiContractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 组织机构guid
	 */
	@TableId
	private String buguid;
	/**
	 * 项目guid
	 */
	private String projguid;
	/**
	 * 签约guid
	 */
	private String tradeguid;
	/**
	 * 认购就是认购guid，签约就是签约guid
	 */
	private String saleguid;
	/**
	 * 起始日期
	 */
	private Date qsdate;
	/**
	 * 业务办理日期
	 */
	private Date ywbldate;
	/**
	 * 客户名字
	 */
	private String cstname;
	/**
	 * 客户电话
	 */
	private String csttel;
	/**
	 * 房间信息
	 */
	private String roominfo;
	/**
	 * 业务员
	 */
	private String ywy;
	/**
	 * 销售状态
	 */
	private String salestatus;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 房款
	 */
	private BigDecimal cjtotal;
	/**
	 * 人民币计算房款
	 */
	private BigDecimal rmbcjtotal;
	/**
	 * 币种
	 */
	private String bz;
	/**
	 * 房间guid
	 */
	private String roomguid;
	/**
	 * 客户guid列表
	 */
	private String cstallguid;
	/**
	 * 销售类型
	 */
	private String saletype;
	/**
	 * 按揭银行
	 */
	private String ajbank;
	/**
	 * 按揭金额
	 */
	private BigDecimal ajtotal;
	/**
	 * 公积金银行
	 */
	private String gjjbank;
	/**
	 * 公积金金额
	 */
	private BigDecimal gjjtotal;
	/**
	 *
	 */
	private String tjr;
	/**
	 *
	 */
	private BigDecimal bldarea;
	/**
	 *
	 */
	private BigDecimal exrate;
	/**
	 * 付款方式
	 */
	private String payformname;
	/**
	 *
	 */
	private Date jzdate;
	/**
	 *
	 */
	private BigDecimal jzamount;
	/**
	 *
	 */
	private BigDecimal sjbctotal;
	/**
	 *
	 */
	private String iszxkbrht;
	/**
	 *
	 */
	private BigDecimal zxtotal;
	/**
	 *
	 */
	private BigDecimal tnarea;
	/**
	 * 建筑价格
	 */
	private BigDecimal price;
	/**
	 * 套内价格
	 */
	private BigDecimal tnprice;
	/**
	 * 总价
	 */
	private BigDecimal total;
	/**
	 * 计算模式
	 */
	private String calmode;
	/**
	 * 折扣
	 */
	private BigDecimal discntvalue;
	/**
	 * 折扣备注
	 */
	private String discntremark;
	/**
	 * 建筑单价
	 */
	private BigDecimal bldcjprice;
	/**
	 * 套内单价
	 */
	private BigDecimal tncjprice;
	/**
	 * 总房款
	 */
	private BigDecimal roomtotal;
	/**
	 *
	 */
	private String zxbz;
	/**
	 *
	 */
	private BigDecimal zxprice;
	/**
	 *
	 */
	private BigDecimal fstotal;
	/**
	 *
	 */
	private Integer earnest;
	/**
	 *
	 */
	private String areastatus;
	/**
	 *
	 */
	private Integer jzstatus;
	/**
	 *
	 */
	private Date jzpzdate;
	/**
	 *
	 */
	private Date canceljzdate;
	/**
	 *
	 */
	private Date canceljzpzdate;
	/**
	 *
	 */
	private Integer pzstatus;
	/**
	 *
	 */
	private Integer isgeneratefail;
	/**
	 *
	 */
	private String failreason;
	/**
	 *
	 */
	private BigDecimal djbldcjprice;
	/**
	 *
	 */
	private BigDecimal djtncjprice;
	/**
	 *
	 */
	private BigDecimal djroomtotal;
	/**
	 * 产品类型
	 */
	private String producttype;
	/**
	 *
	 */
	private Date syncTime;

}
