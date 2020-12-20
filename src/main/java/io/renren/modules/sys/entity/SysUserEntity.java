/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @TableId
  private Long userId;

  /**
   * 用户名
   */
  @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
  private String username;

  /**
   * 密码
   */
  @NotBlank(message = "密码不能为空", groups = AddGroup.class)
  private String password;

  /**
   * 盐
   */
  private String salt;

  /**
   * 邮箱
   */
  @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
  @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
  private String email;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 状态  0：禁用   1：正常
   */
  private Integer status;

  /**
   * 角色ID列表
   */
  @TableField(exist = false)
  private List<Long> roleIdList;

  /**
   * 创建者ID
   */
  private Long createUserId;

  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 1:经理，2：置业顾问
   */
  private Integer appRole;

  private String head;
  /**
   * 统计使用
   */
  @TableField(exist = false)
  private Integer count;

  /**
   * 性别
   */
  private String sex;

  /**
   * 真实姓名
   */
  private String name;

  /**
   * openId
   */
  private String openId;

  /**
   * nickName微信名
   */
  private String nickName;

  /**
   * unionId
   */
  private String unionId;

  /**
   * avatarUrl头像
   */
  private String avatarUrl;

  /**
   * 身份ID
   */
  private String middleTypeId;

  /**
   * 身份
   */
  private String middleTypeName;

  /**
   * 用户名首字母
   */
  private String firstLetter;

  /**
   * 保持登录的天数
   */
  private long keepLoginDays;

  /**
   * 今日是否已签到
   */
  @TableField(exist = false)
  private int isSigned;

  @TableField(exist = false)
  private List<Long> projectIds;
}
