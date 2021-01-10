package io.renren.modules.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.erp.entity.BusiReportWqyEntity;
import io.renren.modules.erp.service.BusiReportWqyService;
import io.renren.modules.sys.controller.AbstractController;
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
 * @date 2021-01-06 17:24:57
 */
@RestController
@RequestMapping("erp/busireportwqy")
public class BusiReportWqyController extends AbstractController {
    @Autowired
    private BusiReportWqyService busiReportWqyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("erp:busireportwqy:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiReportWqyService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listByMy") //未签约
    public R listByMy(@RequestParam Map<String, Object> params){
        String name = getUser().getName();
        IPage<BusiReportWqyEntity> page = busiReportWqyService.page(
                new Query<BusiReportWqyEntity>().getPage(params),
                new QueryWrapper<BusiReportWqyEntity>()
                        .lambda()
                        .gt(BusiReportWqyEntity::getYqdate,0)
                        .like(BusiReportWqyEntity::getUsername,name)
                        .orderByDesc(BusiReportWqyEntity::getQsdate)
        );
        PageUtils pageUtils = new PageUtils(page);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("erp:busireportwqy:info")
    public R info(@PathVariable("id") Integer id){
		BusiReportWqyEntity busiReportWqy = busiReportWqyService.getById(id);

        return R.ok().put("busiReportWqy", busiReportWqy);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("erp:busireportwqy:save")
    public R save(@RequestBody BusiReportWqyEntity busiReportWqy){
		busiReportWqyService.save(busiReportWqy);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("erp:busireportwqy:update")
    public R update(@RequestBody BusiReportWqyEntity busiReportWqy){
		busiReportWqyService.updateById(busiReportWqy);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("erp:busireportwqy:delete")
    public R delete(@RequestBody Integer[] ids){
		busiReportWqyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
