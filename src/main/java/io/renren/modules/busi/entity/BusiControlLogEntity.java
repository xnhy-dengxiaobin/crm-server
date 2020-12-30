package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 销控日志
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-27 13:25:39
 */
@Data
@TableName("busi_control_log")
public class BusiControlLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键，自增长
	 */
	@TableId
	private Integer id;
	/**
	 * 房源id
	 */
	private Integer houseId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 操作人名称
	 */
	private String createName;
	/**
	 * 操作状态
	 */
	private Integer controlStatus;
	/**
	 * 操作人di
	 */
	private Long createId;
	/**
	 * 创建时间
	 */
	private Date createTime;


	/**
	 * 房源id
	 */

	@TableField(exist = false)
	private List<String> houseIds;

}
