package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerBookingEntity;
import io.renren.modules.busi.service.BusiCustomerBookingService;
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
 * @date 2021-01-04 16:42:11
 */
@RestController
@RequestMapping("busi/busicustomerbooking")
public class BusiCustomerBookingController {
    @Autowired
    private BusiCustomerBookingService busiCustomerBookingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busicustomerbooking:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerBookingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busicustomerbooking:info")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerBookingEntity busiCustomerBooking = busiCustomerBookingService.getById(id);

        return R.ok().put("busiCustomerBooking", busiCustomerBooking);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busicustomerbooking:save")
    public R save(@RequestBody BusiCustomerBookingEntity busiCustomerBooking){
		busiCustomerBookingService.save(busiCustomerBooking);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicustomerbooking:update")
    public R update(@RequestBody BusiCustomerBookingEntity busiCustomerBooking){
		busiCustomerBookingService.updateById(busiCustomerBooking);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomerbooking:delete")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerBookingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
