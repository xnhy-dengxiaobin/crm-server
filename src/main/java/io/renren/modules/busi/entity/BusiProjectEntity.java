package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 项目
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@Data
@TableName("busi_project")
public class BusiProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键，自增长
	 */
	@TableId
	private Integer id;
	/**
	 * 项目名称
	 */
	private String name;

	/**
	 * 项目短名称
	 */
	private String shortName;

	private int level;

	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 父级项目
	 */
	private Integer parentId;
  @TableField(exist=false)
  private String ParentName;

}
