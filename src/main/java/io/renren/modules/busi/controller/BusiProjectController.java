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

import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.busi.service.BusiProjectService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 项目
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@RestController
@RequestMapping("busi/busiproject")
public class BusiProjectController {
    @Autowired
    private BusiProjectService busiProjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busiproject:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiProjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busiproject:info")
    public R info(@PathVariable("id") Integer id){
		BusiProjectEntity busiProject = busiProjectService.getById(id);

        return R.ok().put("busiProject", busiProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busiproject:save")
    public R save(@RequestBody BusiProjectEntity busiProject){
		busiProjectService.save(busiProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busiproject:update")
    public R update(@RequestBody BusiProjectEntity busiProject){
		busiProjectService.updateById(busiProject);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busiproject:delete")
    public R delete(@RequestBody Integer[] ids){
		busiProjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
