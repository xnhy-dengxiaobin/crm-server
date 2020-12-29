package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerOrderEntity;
import io.renren.modules.busi.service.BusiCustomerOrderService;
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
@RequestMapping("busi/busicustomerorder")
public class BusiCustomerOrderController {
    @Autowired
    private BusiCustomerOrderService busiCustomerOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busicustomerorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busicustomerorder:info")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerOrderEntity busiCustomerOrder = busiCustomerOrderService.getById(id);

        return R.ok().put("busiCustomerOrder", busiCustomerOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busicustomerorder:save")
    public R save(@RequestBody BusiCustomerOrderEntity busiCustomerOrder){
		busiCustomerOrderService.save(busiCustomerOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicustomerorder:update")
    public R update(@RequestBody BusiCustomerOrderEntity busiCustomerOrder){
		busiCustomerOrderService.updateById(busiCustomerOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomerorder:delete")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
