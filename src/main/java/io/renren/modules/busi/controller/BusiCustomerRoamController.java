package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerRoamEntity;
import io.renren.modules.busi.service.BusiCustomerRoamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 转介路径
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-01 23:36:55
 */
@RestController
@RequestMapping("busi/busicustomerroam")
public class BusiCustomerRoamController {
    @Autowired
    private BusiCustomerRoamService busiCustomerRoamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busicustomerroam:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerRoamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busicustomerroam:info")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerRoamEntity busiCustomerRoam = busiCustomerRoamService.getById(id);

        return R.ok().put("busiCustomerRoam", busiCustomerRoam);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busicustomerroam:save")
    public R save(@RequestBody BusiCustomerRoamEntity busiCustomerRoam){
		busiCustomerRoamService.save(busiCustomerRoam);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicustomerroam:update")
    public R update(@RequestBody BusiCustomerRoamEntity busiCustomerRoam){
		busiCustomerRoamService.updateById(busiCustomerRoam);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomerroam:delete")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerRoamService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
