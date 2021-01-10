package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiBookingEntity;
import io.renren.modules.busi.service.BusiBookingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 认筹表
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-04 16:42:11
 */
@RestController
@RequestMapping("busi/busibooking")
public class BusiBookingController {
    @Autowired
    private BusiBookingService busiBookingService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busibooking:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiBookingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busibooking:info")
    public R info(@PathVariable("id") Integer id){
		BusiBookingEntity busiBooking = busiBookingService.getById(id);

        return R.ok().put("busiBooking", busiBooking);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busibooking:save")
    public R save(@RequestBody BusiBookingEntity busiBooking){
		busiBookingService.save(busiBooking);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busibooking:update")
    public R update(@RequestBody BusiBookingEntity busiBooking){
		busiBookingService.updateById(busiBooking);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busibooking:delete")
    public R delete(@RequestBody Integer[] ids){
		busiBookingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
