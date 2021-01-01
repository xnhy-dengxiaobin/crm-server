/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.busi.entity.MiddleTypeEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

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
   * 根据projectId 查询正常跟进客户
   * @param projectIds
   * @return
   */
  List<SysUserEntity> queryNormalFollow(@Param("projectIds") List<Integer> projectIds);
  /**
   * 根据projectId 查询逾期客户
   * @param projectIds
   * @return
   */
  List<SysUserEntity> queryTimeoutList(@Param("projectIds") List<Integer> projectIds);

  /**
   * 根据projectId 查询置业顾问
   * @return
   */
  List<SysUserEntity> querySalesUserByProjectId(Map<String, Object> params);

  int updateTeam(@Param("teamId") Long paramKey, @Param("teamName") String paramValue,  @Param("userId") Long userId);


    List<SysUserEntity> slctMiddlemen(Map<String, Object> params);

  List<MiddleTypeEntity> selectMiddleMan(Map<String, Object> params);

  Integer cntMiddleMan(Map<String, Object> params);
}
