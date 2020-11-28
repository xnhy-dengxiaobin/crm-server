package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 项目户型图
 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-28 10:32:11
 */
@Data
@TableName("busi_house_layout")
public class HouseLayoutEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 项目名称
	 */
	@TableField(exist = false)
	private String projectName;

	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 图片说明
	 */
	private String memo;
	/**
	 * 图片路径
	 */
	private String path;

}
