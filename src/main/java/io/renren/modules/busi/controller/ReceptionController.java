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

import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.service.ReceptionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 接待记录
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-29 00:51:30
 */
@RestController
@RequestMapping("busi/reception")
public class ReceptionController {
    @Autowired
    private ReceptionService receptionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:reception:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = receptionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:reception:info")
    public R info(@PathVariable("id") Integer id){
		ReceptionEntity reception = receptionService.getById(id);

        return R.ok().put("reception", reception);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:reception:save")
    public R save(@RequestBody ReceptionEntity reception){
		receptionService.save(reception);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:reception:update")
    public R update(@RequestBody ReceptionEntity reception){
		receptionService.updateById(reception);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:reception:delete")
    public R delete(@RequestBody Integer[] ids){
		receptionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
