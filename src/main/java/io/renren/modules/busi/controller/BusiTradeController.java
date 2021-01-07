package io.renren.modules.busi.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiTradeEntity;
import io.renren.modules.busi.service.BusiTradeService;
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
 * @date 2021-01-04 16:42:10
 */
@RestController
@RequestMapping("busi/busitrade")
public class BusiTradeController {
    @Autowired
    private BusiTradeService busiTradeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiTradeService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busitrade:info")
    public R info(@PathVariable("id") Integer id){
		BusiTradeEntity busiTrade = busiTradeService.getById(id);

        return R.ok().put("busiTrade", busiTrade);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busitrade:save")
    public R save(@RequestBody BusiTradeEntity busiTrade){
		busiTradeService.save(busiTrade);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busitrade:update")
    public R update(@RequestBody BusiTradeEntity busiTrade){
		busiTradeService.updateById(busiTrade);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busitrade:delete")
    public R delete(@RequestBody Integer[] ids){
		busiTradeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
