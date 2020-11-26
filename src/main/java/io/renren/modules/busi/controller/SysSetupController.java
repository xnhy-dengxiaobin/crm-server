package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.SysSetupEntity;
import io.renren.modules.busi.service.SysSetupService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@RestController
@RequestMapping("busi/syssetup")
public class SysSetupController {
    @Autowired
    private SysSetupService sysSetupService;


    /**
     * 列表
     */
    @RequestMapping("/listByKey")
    public R listByKey(@RequestParam Map<String, String> params){
        String key = params.get("key");
        List<SysSetupEntity> rs = sysSetupService.list(new QueryWrapper<SysSetupEntity>().eq("key_n", key));
        return R.ok().put("list", rs);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:syssetup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysSetupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:syssetup:info")
    public R info(@PathVariable("id") Integer id){
		SysSetupEntity sysSetup = sysSetupService.getById(id);

        return R.ok().put("sysSetup", sysSetup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:syssetup:save")
    public R save(@RequestBody SysSetupEntity sysSetup){
		sysSetupService.save(sysSetup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:syssetup:update")
    public R update(@RequestBody SysSetupEntity sysSetup){
		sysSetupService.updateById(sysSetup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:syssetup:delete")
    public R delete(@RequestBody Integer[] ids){
		sysSetupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
