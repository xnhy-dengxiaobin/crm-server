package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiOrderEntity;
import io.renren.modules.busi.service.BusiOrderService;
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
 * @date 2020-12-29 00:08:46
 */
@RestController
@RequestMapping("busi/busiorder")
public class BusiOrderController {
    @Autowired
    private BusiOrderService busiOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busiorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busiorder:info")
    public R info(@PathVariable("id") Integer id){
		BusiOrderEntity busiOrder = busiOrderService.getById(id);

        return R.ok().put("busiOrder", busiOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busiorder:save")
    public R save(@RequestBody BusiOrderEntity busiOrder){
		busiOrderService.save(busiOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busiorder:update")
    public R update(@RequestBody BusiOrderEntity busiOrder){
		busiOrderService.updateById(busiOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busiorder:delete")
    public R delete(@RequestBody Integer[] ids){
		busiOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
