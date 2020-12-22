package io.renren.modules.busi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 个人推广码
 * 
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-21 23:17:58
 */
@Data
@TableName("busi_wxuser_qr")
public class WxuserQrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * wx微信唯一
	 */

	private String unionId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * fileid
	 */
	private String fileid;
	/**
	 * 类型
	 */
	private String contenttype;
	/**
	 * 文件名称
	 */
	private String filename;
	/**
	 * 路径
	 */
	private String dirId;
	/**
	 * url
	 */
	private String url;

}
