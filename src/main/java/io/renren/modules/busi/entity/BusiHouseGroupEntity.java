package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2020-11-26 23:05:13
 */
@Data
@TableName("busi_house_group")
public class BusiHouseGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增
	 */
	@TableId
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 类型：栋/单元
	 */
	private String type;
	/**
	 * 意向项目,关联busi_project
	 */
	private Integer parentId;
	/**
	 * 总数
	 */
	private String sum;
	/**
	 * 已售出数量
	 */
	private String saleSum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;


	@TableField(exist = false)
	private Integer wx;


	@TableField(exist = false)
	private Integer ds;

	@TableField(exist = false)
	private Integer xk;

	@TableField(exist = false)
	private Integer rg;

	@TableField(exist = false)
	private Integer qy;

	@TableField(exist = false)
	private Integer zs;


}
