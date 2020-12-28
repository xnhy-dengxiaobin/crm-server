package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiHouseEntity;
import io.renren.modules.busi.service.BusiHouseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



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
    @RequestMapping("/listByGroupId/{gid}")
    public R listByGroupId(@PathVariable("gid") Integer gid){
        List<BusiHouseEntity> list = busiHouseService.list(new QueryWrapper<BusiHouseEntity>().eq("group_id", gid));
        return R.ok().put("list", list);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busihouse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiHouseService.listPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
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
