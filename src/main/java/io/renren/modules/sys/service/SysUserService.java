/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysUserService extends IService<SysUserEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	/**
	 * 根据wxunionId，查询系统用户
	 */
	SysUserEntity queryByUnionId(String unionId);

	/**
	 * 保存用户
	 */
	void saveUser(SysUserEntity user);

	/**
	 * 保存wx用户
	 */
	void saveWxUser(SysUserEntity user);

	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);

	/**
	 * 修改团队
	 */
	void updateTeam(SysUserEntity user);

	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);
  /**
   * 根据projectId 查询正常跟进客户
   * @param projectIds
   * @return
   */
  List<SysUserEntity> queryNormalFollow(List<Integer> projectIds);
  /**
   * 根据projectId 查询逾期客户
   * @param projectIds
   * @return
   */
  List<SysUserEntity> queryTimeoutList(List<Integer> projectIds);

  /**
   * 根据projectId 查询置业顾问
   * @return
   */
  List<SysUserEntity> querySalesUserByProjectId(List<Integer> projectIds);

	/**
	 * 查询经纪人信息
	 * @param params
	 * @return
	 */
    List<SysUserEntity> qryMiddlemen(Map<String, Object> params);

	PageUtils middleManPage(Map<String, Object> params);

	void audit(Map<String, Object> params);
}
