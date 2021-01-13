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
 * @date 2021-01-13 17:34:09
 */
@Data
@TableName("busi_press_log")
public class BusiPressLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private String type;
	/**
	 * wrg: saleguid wjk:saleguid wqy:orderguid
	 */
	private String busId;
	/**
	 *
	 */
	private Date date;
	/**
	 *
	 */
	private String cause;

}
