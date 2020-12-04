package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 员工签到表

 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-04 20:26:45
 */
@Data
@TableName("busi_sign_on")
public class SignOnEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 二维码code
	 */
	private String code;
	/**
	 * 员工id
	 */
	private Integer userId;
	/**
	 * 签到日期
	 */
	private Date signOnDate;
	/**
	 * 记录创建时间
	 */
	private Date createdTime;

}
