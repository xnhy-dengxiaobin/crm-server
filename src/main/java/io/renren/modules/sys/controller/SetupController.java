package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.SetupEntity;
import io.renren.modules.sys.service.SetupService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-19 23:12:44
 */
@RestController
@RequestMapping("sys/setup")
public class SetupController {
    @Autowired
    private SetupService setupService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:setup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = setupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:setup:info")
    public R info(@PathVariable("id") Integer id){
		SetupEntity setup = setupService.getById(id);

        return R.ok().put("setup", setup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:setup:save")
    public R save(@RequestBody SetupEntity setup){
		setupService.save(setup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:setup:update")
    public R update(@RequestBody SetupEntity setup){
		setupService.updateById(setup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:setup:delete")
    public R delete(@RequestBody Integer[] ids){
		setupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/lst")
    public R lst(@RequestParam Map<String, Object> params){
        List<SetupEntity> setupEntities = setupService.qry(params);
        return R.ok().put("list", setupEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/batchList")
    public R lst(@RequestBody List<String> keys){
        List<SetupEntity> setupEntities = setupService.queryBatch(keys);
        return R.ok().put("list", setupEntities);
    }
}
