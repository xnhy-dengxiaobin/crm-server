package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接待记录
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-29 00:51:30
 */
@Data
@TableName("busi_reception")
public class ReceptionEntity implements Serializable {
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
	 * 接待客户的id
	 */
	private Integer customerId;
	/**
	 * 来访人数
	 */
	private Integer cnt;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 分配的置业顾问id
	 */
	private Integer salerId;
	/**
	 * 接待员id
	 */
	private Integer receptionistId;
	/**
	 * 访问时间
	 */
	private Date createTime;
	/**
	 * 处理状态:0-未处理；10-已处理
	 */
	private Integer status;

}
