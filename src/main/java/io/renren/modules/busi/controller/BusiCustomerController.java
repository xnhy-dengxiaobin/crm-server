package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.service.BusiCustomerService;
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
 * @date 2020-11-22 23:09:03
 */
@RestController
@RequestMapping("busi/busicustomer")
public class BusiCustomerController {
    @Autowired
    private BusiCustomerService busiCustomerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/allList")
    public R allList(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerEntity busiCustomer = busiCustomerService.getById(id);

        return R.ok().put("busiCustomer", busiCustomer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BusiCustomerEntity busiCustomer){
		busiCustomerService.save(busiCustomer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BusiCustomerEntity busiCustomer){
		busiCustomerService.updateById(busiCustomer);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomer:delete")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
