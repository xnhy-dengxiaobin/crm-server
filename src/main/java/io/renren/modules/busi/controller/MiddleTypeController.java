package io.renren.modules.busi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.busi.entity.MiddleTypeEntity;
import io.renren.modules.busi.service.MiddleTypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 中介来源，类型
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-14 22:01:22
 */
@RestController
@RequestMapping("busi/middletype")
public class MiddleTypeController extends AbstractController {
    @Autowired
    private MiddleTypeService middleTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:middletype:list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = middleTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:middletype:info")
    public R info(@PathVariable("id") Integer id) {
        MiddleTypeEntity middleType = middleTypeService.getById(id);

        return R.ok().put("middleType", middleType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:middletype:save")
    public R save(@RequestBody MiddleTypeEntity middleType) {
        middleTypeService.save(middleType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:middletype:update")
    public R update(@RequestBody MiddleTypeEntity middleType) {
        middleTypeService.updateById(middleType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:middletype:delete")
    public R delete(@RequestBody Integer[] ids) {
        middleTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /*********************以下为自定义************************/
    /**
     * 列表
     */
    @RequestMapping("/lst")
    public R lst(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        List<MiddleTypeEntity> middleTypeEntities = middleTypeService.qryLst(params);

        return R.ok().put("list", middleTypeEntities);
    }
    /**
     * wx列表
     */
    @RequestMapping("/wxList")
    public R wxList(@RequestBody Map<String, Object> params) {
        List<MiddleTypeEntity> middleTypeEntities = middleTypeService.qryWxList(params);
        return R.ok().put("list", middleTypeEntities);
    }
}
