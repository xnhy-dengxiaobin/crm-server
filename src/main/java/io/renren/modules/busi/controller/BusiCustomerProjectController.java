package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerProjectEntity;
import io.renren.modules.busi.service.BusiCustomerProjectService;
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
 * @date 2020-11-28 11:49:14
 */
@RestController
@RequestMapping("busi/busicustomerproject")
public class BusiCustomerProjectController {
    @Autowired
    private BusiCustomerProjectService busiCustomerProjectService;


    /**
     * 列表
     */
    @RequestMapping("/listByCustomer/{cid}")
    public R listByCustomer(@PathVariable("cid") Integer cid){
        List<BusiCustomerProjectEntity> list = busiCustomerProjectService.list(new QueryWrapper<BusiCustomerProjectEntity>().eq("customer_id", cid));
        return R.ok().put("list", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/listByCustomerId/{cid}")
    public R listByCustomerId(@PathVariable("cid") Integer cid){
        List<BusiCustomerProjectEntity> list = busiCustomerProjectService.list(new QueryWrapper<BusiCustomerProjectEntity>().eq("customer_id", cid));
        return R.ok().put("list", list);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busicustomerproject:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerProjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busicustomerproject:info")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerProjectEntity busiCustomerProject = busiCustomerProjectService.getById(id);

        return R.ok().put("busiCustomerProject", busiCustomerProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BusiCustomerProjectEntity busiCustomerProject){
		busiCustomerProjectService.save(busiCustomerProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicustomerproject:update")
    public R update(@RequestBody BusiCustomerProjectEntity busiCustomerProject){
		busiCustomerProjectService.updateById(busiCustomerProject);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomerproject:delete")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerProjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
