package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 中介来源，类型
 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-14 22:01:22
 */
@Data
@TableName("busi_middle_type")
public class MiddleTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键，自增长
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private Integer status;

}
