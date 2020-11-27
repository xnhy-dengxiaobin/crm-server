package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiHouseGroupEntity;
import io.renren.modules.busi.service.BusiHouseGroupService;
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
 * @date 2020-11-26 23:05:13
 */
@RestController
@RequestMapping("busi/busihousegroup")
public class BusiHouseGroupController {
    @Autowired
    private BusiHouseGroupService busiHouseGroupService;


    /**
     * 列表
     */
    @RequestMapping("/listByProjectId/{pid}")
    public R listByProjectId(@PathVariable("pid") Integer pid){
        List<BusiHouseGroupEntity> list = busiHouseGroupService.list(new QueryWrapper<BusiHouseGroupEntity>().eq("parent_id", pid).eq("type", "栋"));
        return R.ok().put("list", list);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busihousegroup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiHouseGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busihousegroup:info")
    public R info(@PathVariable("id") Integer id){
		BusiHouseGroupEntity busiHouseGroup = busiHouseGroupService.getById(id);

        return R.ok().put("busiHouseGroup", busiHouseGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busihousegroup:save")
    public R save(@RequestBody BusiHouseGroupEntity busiHouseGroup){
		busiHouseGroupService.save(busiHouseGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busihousegroup:update")
    public R update(@RequestBody BusiHouseGroupEntity busiHouseGroup){
		busiHouseGroupService.updateById(busiHouseGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busihousegroup:delete")
    public R delete(@RequestBody Integer[] ids){
		busiHouseGroupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
