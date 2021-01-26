/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.busi.entity.BusiUserProjectEntity;
import io.renren.modules.busi.service.BusiProjectService;
import io.renren.modules.busi.service.BusiUserProjectService;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BusiUserProjectService projectService;
	@Autowired
  private BusiProjectService busiProjectService;

	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}
	protected List<Integer> getProjectIds(){
		Long userId = getUserId();
		List<BusiUserProjectEntity> list = projectService.list(new QueryWrapper<BusiUserProjectEntity>().lambda().select(BusiUserProjectEntity::getProjectId).eq(BusiUserProjectEntity::getUserId, userId));
		List<Integer> rs = new ArrayList<>();
		for (BusiUserProjectEntity busiUserProjectEntity : list) {
			rs.add(busiUserProjectEntity.getProjectId());
		}
		return rs;
	}
	protected Long getUserId() {
		return getUser().getUserId();
	}

  protected Integer getProjectId(){
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String projectId = request.getHeader("projectId");
    if(projectId != null && !projectId.equals("")){
      return Integer.parseInt(projectId);
    }
    return null;
  }
  protected List<Integer> getPurviewProjectIds(){
    Long userId = getUserId();
    List<BusiUserProjectEntity> list = projectService.list(new QueryWrapper<BusiUserProjectEntity>().lambda().select(BusiUserProjectEntity::getProjectId).eq(BusiUserProjectEntity::getUserId, userId));
    List<Integer> rs = new ArrayList<>();
    for (BusiUserProjectEntity busiUserProjectEntity : list) {
      rs.add(busiUserProjectEntity.getProjectId());
    }
    List<BusiProjectEntity> list1 = busiProjectService.list(new QueryWrapper<BusiProjectEntity>().lambda().select(BusiProjectEntity::getId).or(item->item.in(BusiProjectEntity::getId,rs)).or(item-> item.in(BusiProjectEntity::getParentId,rs)));
    List<Integer> rs1 = new ArrayList<>();
    for (BusiProjectEntity item : list1) {
      rs1.add(item.getId());
    }
    return rs1;
  }
}
