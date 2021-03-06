/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.busi.service.BusiUserProjectService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.form.PasswordForm;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private BusiUserProjectService busiUserProjectService;


    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 所有置业顾问列表
     */
    @GetMapping("/salesAll")
    public R salesAll(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        List<SysUserEntity> list = sysUserService.list(new QueryWrapper<SysUserEntity>()
                .lambda().eq(SysUserEntity::getAppRole, 2).eq(SysUserEntity::getStatus, 1));

        return R.ok().put("list", list);
    }

    /**
     * 该项目下所有置业顾问
     */
    @PostMapping("/salesByProjectId")
    public R salesByProjectId(@RequestParam(required = false) Long projectId) {
        List<Integer> projectIds = getProjectIds();
        List list = sysUserService.querySalesUserByProjectId(projectIds);

        return R.ok().put("datas", list);
    }

    /**
     * 项目下、某个身份下所有经纪人
     */
    @PostMapping("/slctMiddlemen")
    public R slctMiddlemen(@RequestBody Map<String, Object> params) {
        List<SysUserEntity> list = sysUserService.qryMiddlemen(params);

        return R.ok().put("datas", list);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        List<BusiProjectEntity> busiUserProjects = busiUserProjectService.queryProjectByUser(userId);
        if(CollectionUtils.isNotEmpty(busiUserProjects)){
            if(CollectionUtils.isEmpty(user.getProjectIds())){
                user.setProjectIds(new ArrayList<>());
            }
            busiUserProjects.forEach(up ->{
                user.getProjectIds().add(Long.valueOf(up.getId()));
            });
        }

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        final String pinyin = PinyinHelper.toPinyin(user.getUsername(), PinyinStyleEnum.FIRST_LETTER, StringUtil.EMPTY);

        user.setCreateUserId(getUserId());
        sysUserService.saveUser(user);

        return R.ok();
    }

    /**
     * 保存wx用户
     */
    @SysLog("保存wx用户")
    @PostMapping("/wxSave")
    public R wxSave(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        final String pinyin = PinyinHelper.toPinyin(user.getUsername(), PinyinStyleEnum.FIRST_LETTER, StringUtil.EMPTY);
        user.setCreateUserId(1l);
        sysUserService.saveWxUser(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        //TODO: 用户名首字母拼音 //https://github.com/houbb/pinyin
        final String pinyin = PinyinHelper.toPinyin(user.getUsername(), PinyinStyleEnum.FIRST_LETTER, StringUtil.EMPTY);

        user.setCreateUserId(getUserId());
        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }

    /**
     * 所有经纪人列表
     */
    @GetMapping("/listMiddleMan")
    public R listMiddleMan(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = sysUserService.middleManPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 修改用户
     */
    @SysLog("审核经纪人")
    @PostMapping("/auditMiddleMan")
    @RequiresPermissions("sys:user:audit")
    public R auditMiddleMan(@RequestBody Map<String, Object> params) {
        SysUserEntity user = new SysUserEntity();

        sysUserService.audit(params);

        return R.ok();
    }
}
