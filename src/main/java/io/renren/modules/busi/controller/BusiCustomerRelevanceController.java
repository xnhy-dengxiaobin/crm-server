package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerRelevanceEntity;
import io.renren.modules.busi.service.BusiCustomerRelevanceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



/**
 *
 *联名用户
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-28 11:49:13
 */
@RestController
@RequestMapping("busi/busicustomerrelevance")
public class BusiCustomerRelevanceController {
    @Autowired
    private BusiCustomerRelevanceService busiCustomerRelevanceService;

    /**
     * 列表
     */
    @RequestMapping("/listByCustomerId/{cid}")
    public R listByCustomerId(@PathVariable("cid") Integer cid){
        List<BusiCustomerRelevanceEntity> list = busiCustomerRelevanceService.list(new QueryWrapper<BusiCustomerRelevanceEntity>()
                .eq("customer_id", cid));
        return R.ok().put("list", list);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busicustomerrelevance:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerRelevanceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busicustomerrelevance:info")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerRelevanceEntity busiCustomerRelevance = busiCustomerRelevanceService.getById(id);

        return R.ok().put("busiCustomerRelevance", busiCustomerRelevance);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BusiCustomerRelevanceEntity busiCustomerRelevance){
		busiCustomerRelevanceService.save(busiCustomerRelevance);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicustomerrelevance:update")
    public R update(@RequestBody BusiCustomerRelevanceEntity busiCustomerRelevance){
		busiCustomerRelevanceService.updateById(busiCustomerRelevance);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
    public R delete(@PathVariable("id") Integer id){
		busiCustomerRelevanceService.removeById(id);
        return R.ok();
    }

}
