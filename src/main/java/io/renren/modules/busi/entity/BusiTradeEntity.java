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
 * @date 2021-01-14 14:01:48
 */
@Data
@TableName("busi_trade")
public class BusiTradeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String buguid;
	/**
	 *
	 */
	private String projguid;
	/**
	 *
	 */
	private String tradeguid;
	/**
	 *
	 */
	private String saleguid;
	/**
	 *
	 */
	private Date qsdate;
	/**
	 *
	 */
	private Date ywbldate;
	/**
	 *
	 */
	private String cstname;
	/**
	 *
	 */
	private String csttel;
	/**
	 *
	 */
	private String roominfo;
	/**
	 *
	 */
	private String ywy;
	/**
	 *
	 */
	private String salestatus;
	/**
	 *
	 */
	private String status;
	/**
	 *
	 */
	private BigDecimal cjtotal;
	/**
	 *
	 */
	private BigDecimal rmbcjtotal;
	/**
	 *
	 */
	private String bz;
	/**
	 *
	 */
	private String roomguid;
	/**
	 *
	 */
	private String cstallguid;
	/**
	 *
	 */
	private String saletype;
	/**
	 *
	 */
	private String ajbank;
	/**
	 *
	 */
	private BigDecimal ajtotal;
	/**
	 *
	 */
	private String gjjbank;
	/**
	 *
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
	 *
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
	private Integer iszxkbrht;
	/**
	 *
	 */
	private BigDecimal zxtotal;
	/**
	 *
	 */
	private BigDecimal tnarea;
	/**
	 *
	 */
	private BigDecimal price;
	/**
	 *
	 */
	private BigDecimal tnprice;
	/**
	 *
	 */
	private BigDecimal total;
	/**
	 *
	 */
	private String calmode;
	/**
	 *
	 */
	private BigDecimal discntvalue;
	/**
	 *
	 */
	private String discntremark;
	/**
	 *
	 */
	private BigDecimal bldcjprice;
	/**
	 *
	 */
	private BigDecimal tncjprice;
	/**
	 *
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
	 *
	 */
	private String producttype;
	/**
	 *
	 */
	private Date syncTime;
  private int dataPrepared;
}
