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

import io.renren.modules.busi.entity.PrepareEntity;
import io.renren.modules.busi.service.PrepareService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * WX报备表
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-20 11:18:56
 */
@RestController
@RequestMapping("busi/prepare")
public class PrepareController {
    @Autowired
    private PrepareService prepareService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:prepare:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = prepareService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:prepare:info")
    public R info(@PathVariable("id") Integer id){
		PrepareEntity prepare = prepareService.getById(id);

        return R.ok().put("prepare", prepare);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:prepare:save")
    public R save(@RequestBody PrepareEntity prepare){
		prepareService.save(prepare);

        return R.ok();
    }

    /**
     * wx保存
     */
    @RequestMapping("/wx/save")
    public R wxsave(@RequestBody PrepareEntity prepare){
        prepareService.save(prepare);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:prepare:update")
    public R update(@RequestBody PrepareEntity prepare){
		prepareService.updateById(prepare);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:prepare:delete")
    public R delete(@RequestBody Integer[] ids){
		prepareService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 查询和客户相关联的列表
     */
    @RequestMapping("/lstPage")
    public R lstPage(@RequestBody Map<String, Object> params){
        PageUtils page = prepareService.lstPage(params);
        return R.ok().put("page", page);
    }
}
