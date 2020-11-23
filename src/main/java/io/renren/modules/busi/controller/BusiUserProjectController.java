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

import io.renren.modules.busi.entity.BusiUserProjectEntity;
import io.renren.modules.busi.service.BusiUserProjectService;
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
@RequestMapping("busi/busiuserproject")
public class BusiUserProjectController {
    @Autowired
    private BusiUserProjectService busiUserProjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busiuserproject:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiUserProjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busiuserproject:info")
    public R info(@PathVariable("id") Integer id){
		BusiUserProjectEntity busiUserProject = busiUserProjectService.getById(id);

        return R.ok().put("busiUserProject", busiUserProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busiuserproject:save")
    public R save(@RequestBody BusiUserProjectEntity busiUserProject){
		busiUserProjectService.save(busiUserProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busiuserproject:update")
    public R update(@RequestBody BusiUserProjectEntity busiUserProject){
		busiUserProjectService.updateById(busiUserProject);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busiuserproject:delete")
    public R delete(@RequestBody Integer[] ids){
		busiUserProjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
