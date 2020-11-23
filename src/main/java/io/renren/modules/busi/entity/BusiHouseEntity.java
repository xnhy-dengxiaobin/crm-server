package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
	/**
	 * 创建时间
	 */
	private Date createTime;

}
