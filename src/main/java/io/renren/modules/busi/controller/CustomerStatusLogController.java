package io.renren.modules.busi.controller;

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

import io.renren.modules.busi.entity.CustomerStatusLogEntity;
import io.renren.modules.busi.service.CustomerStatusLogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-22 22:29:25
 */
@RestController
@RequestMapping("busi/customerstatuslog")
public class CustomerStatusLogController {
    @Autowired
    private CustomerStatusLogService customerStatusLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:customerstatuslog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = customerStatusLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:customerstatuslog:info")
    public R info(@PathVariable("id") Integer id){
		CustomerStatusLogEntity customerStatusLog = customerStatusLogService.getById(id);

        return R.ok().put("customerStatusLog", customerStatusLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:customerstatuslog:save")
    public R save(@RequestBody CustomerStatusLogEntity customerStatusLog){
		customerStatusLogService.save(customerStatusLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:customerstatuslog:update")
    public R update(@RequestBody CustomerStatusLogEntity customerStatusLog){
		customerStatusLogService.updateById(customerStatusLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:customerstatuslog:delete")
    public R delete(@RequestBody Integer[] ids){
		customerStatusLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/lst")
    public R lst(@RequestBody Map<String, Object> params){
        List<CustomerStatusLogEntity> list = customerStatusLogService.queryList(params);

        return R.ok().put("list", list);
    }
}
