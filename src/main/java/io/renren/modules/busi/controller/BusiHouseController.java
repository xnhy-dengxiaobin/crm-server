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

import io.renren.modules.busi.entity.BusiHouseEntity;
import io.renren.modules.busi.service.BusiHouseService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 房源
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@RestController
@RequestMapping("busi/busihouse")
public class BusiHouseController {
    @Autowired
    private BusiHouseService busiHouseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busihouse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiHouseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busihouse:info")
    public R info(@PathVariable("id") Integer id){
		BusiHouseEntity busiHouse = busiHouseService.getById(id);

        return R.ok().put("busiHouse", busiHouse);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busihouse:save")
    public R save(@RequestBody BusiHouseEntity busiHouse){
		busiHouseService.save(busiHouse);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busihouse:update")
    public R update(@RequestBody BusiHouseEntity busiHouse){
		busiHouseService.updateById(busiHouse);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busihouse:delete")
    public R delete(@RequestBody Integer[] ids){
		busiHouseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
