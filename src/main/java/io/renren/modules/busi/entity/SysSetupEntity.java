package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@Data
@TableName("sys_setup")
public class SysSetupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id 自增长
	 */
	@TableId
	private Integer id;
	/**
	 * key
	 */
	private String key;
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

}
