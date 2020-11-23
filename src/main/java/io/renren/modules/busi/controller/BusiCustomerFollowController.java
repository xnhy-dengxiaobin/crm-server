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

import io.renren.modules.busi.entity.BusiCustomerFollowEntity;
import io.renren.modules.busi.service.BusiCustomerFollowService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@RestController
@RequestMapping("busi/busicustomerfollow")
public class BusiCustomerFollowController {
    @Autowired
    private BusiCustomerFollowService busiCustomerFollowService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busicustomerfollow:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerFollowService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busicustomerfollow:info")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerFollowEntity busiCustomerFollow = busiCustomerFollowService.getById(id);

        return R.ok().put("busiCustomerFollow", busiCustomerFollow);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busicustomerfollow:save")
    public R save(@RequestBody BusiCustomerFollowEntity busiCustomerFollow){
		busiCustomerFollowService.save(busiCustomerFollow);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicustomerfollow:update")
    public R update(@RequestBody BusiCustomerFollowEntity busiCustomerFollow){
		busiCustomerFollowService.updateById(busiCustomerFollow);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomerfollow:delete")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerFollowService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
