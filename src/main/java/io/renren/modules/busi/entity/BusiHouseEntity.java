package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 房源
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@Data
@TableName("busi_house")
public class BusiHouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键，自增长
	 */
	@TableId
	private Integer id;
	/**
	 * 房间名称
	 */
	private String name;
	/**
	 * 户型
	 */
	private String houseType;
	/**
	 * 房间结构
	 */
	private String houseStructure;
	/**
	 * 楼层
	 */
	private Integer floor;
	/**
	 * 朝向
	 */
	private String orientation;
	/**
	 * 建筑面积
	 */
	private Double floorArea;
	/**
	 * 套内面积
	 */
	private Double insideFloorArea;
	/**
	 * 建筑单价
	 */
	private BigDecimal floorAreaPrice;
	/**
	 * 套内单价
	 */
	private BigDecimal insideFloorAreaPrice;
	/**
	 * 标准总价
	 */
	private BigDecimal totalPrices;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 项目id
	 */
	private Integer projectId;


	private Integer groupId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 房间guid roomguid
	 */
	private String guid;
	/**
	 * 项目guid projguid
	 */
	private String projectGuid;
	/**
	 * 楼栋guid bldguid
	 */
	private String bldGuid;
	/**
	 * 单元号 unitno
	 */
	private String unit;
	/**
	 * 房间号 no
	 */
	private String no;
	/**
	 * 房间号-带楼层 room
	 */
	private String room;
	/**
	 * 房间编号 roomcode
	 */
	private String roomCode;
	/**
	 * 出售、出租 salerentable
	 */
	private String saleRentable;
	/**
	 * 销控状态1:被销控
	 */
	private Integer control;
	/**
	 * 销控日期 slcontroldate
	 */
	private Date controlDate;

	private String  buildingId;
	/**
	 * 同步时间
	 */
	private Date syncTime;

	@TableField(exist = false)
	private String projectName;


	@TableField(exist = false)
	private String groupName;

}
