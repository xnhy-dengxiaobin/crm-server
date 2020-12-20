package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-19 23:12:44
 */
@Data
@TableName("sys_setup")
public class SetupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id 自增长
	 */
	@TableId
	private Integer id;
	/**
	 * key
	 */
	private String keyN;
	/**
	 * value
	 */
	private String value;
	/**
	 * 排序
	 */
	private Float sort;
	/**
	 * 0：弃用
	 */
	private Integer status;
	/**
	 * 上级
	 */
	private Integer parentId;
	/**
	 * 从顶级到目前级别的id-路径
	 */
	private String tree;

}
