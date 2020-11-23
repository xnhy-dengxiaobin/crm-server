package io.renren.modules.busi.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.busi.entity.SysUserTokenEntity;
import io.renren.modules.busi.service.SysUserTokenService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 系统用户Token
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@RestController
@RequestMapping("busi/sysusertoken")
public class SysUserTokenController {
    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:sysusertoken:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserTokenService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:sysusertoken:info")
    public R info(@PathVariable("id") Integer id){
		SysUserTokenEntity sysUserToken = sysUserTokenService.getById(id);

        return R.ok().put("sysUserToken", sysUserToken);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:sysusertoken:save")
    public R save(@RequestBody SysUserTokenEntity sysUserToken){
		sysUserTokenService.save(sysUserToken);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:sysusertoken:update")
    public R update(@RequestBody SysUserTokenEntity sysUserToken){
		sysUserTokenService.updateById(sysUserToken);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:sysusertoken:delete")
    public R delete(@RequestBody Integer[] ids){
		sysUserTokenService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
