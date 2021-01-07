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
 * @date 2021-01-04 16:42:10
 */
@Data
@TableName("busi_trade")
public class BusiTradeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * erp中的id
	 */
	private String guid;
	/**
	 * 认购/签约房间id
	 */
	private Integer roomId;
	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 房间状态：1预留 2小订 3认购 4签约
	 */
	private Integer roomStatus;
	/**
	 * 认购/签约状态 -1关闭 0未处理 1激活
	 */
	private Integer status;
	/**
	 * 关闭原因：作废、转签约。。
	 */
	private String closeReason;
	/**
	 * 认购订单id
	 */
	private Integer orderId;
	/**
	 * 置业顾问id 待定
	 */
	private Integer salerId;
	/**
	 * 生成时间 待定
	 */
	private Date createdTime;
	/**
	 * 客户guid
	 */
	private String cstAllGuid;
	/**
	 * 客户姓名
	 */
	private String cstAllName;
	/**
	 * 同步时间
	 */
	private Date syncTime;
	/**
	 * 同步标记
	 */
	private String syncFlag;

}
