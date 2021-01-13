package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiPressLogEntity;
import io.renren.modules.busi.service.BusiPressLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-13 17:34:09
 */
@RestController
@RequestMapping("busi/busipresslog")
public class BusiPressLogController {
    @Autowired
    private BusiPressLogService busiPressLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busipresslog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiPressLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busipresslog:info")
    public R info(@PathVariable("id") Integer id){
		BusiPressLogEntity busiPressLog = busiPressLogService.getById(id);

        return R.ok().put("busiPressLog", busiPressLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BusiPressLogEntity busiPressLog){
		busiPressLogService.save(busiPressLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busipresslog:update")
    public R update(@RequestBody BusiPressLogEntity busiPressLog){
		busiPressLogService.updateById(busiPressLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busipresslog:delete")
    public R delete(@RequestBody Integer[] ids){
		busiPressLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
