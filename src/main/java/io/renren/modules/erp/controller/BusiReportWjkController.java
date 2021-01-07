package io.renren.modules.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.erp.entity.BusiReportWjkEntity;
import io.renren.modules.erp.service.BusiReportWjkService;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 *
 *1-未放贷；2-认购未交定金；3-签约未交定金；4-认购未交房款；5-签约未交房款    busi_report_wjk表
 * @author
 * @email 870455116@qq.com
 * @date 2021-01-06 17:24:58
 */
@RestController
@RequestMapping("erp/busireportwjk")
public class BusiReportWjkController extends AbstractController {
    @Autowired
    private BusiReportWjkService busiReportWjkService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("erp:busireportwjk:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiReportWjkService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/listByMy") //1-未放贷；
    public R listByMy(@RequestParam Map<String, Object> params){
        String name = getUser().getName();
        IPage<BusiReportWjkEntity> page = busiReportWjkService.page(
                new Query<BusiReportWjkEntity>().getPage(params),
                new QueryWrapper<BusiReportWjkEntity>()
                        .lambda()
                        .eq(BusiReportWjkEntity::getType,params.get("type"))
                        .gt(BusiReportWjkEntity::getYqdate,0)
                        .like(BusiReportWjkEntity::getUsername,name)
                        .orderByDesc(BusiReportWjkEntity::getQsdate)
        );
        PageUtils pageUtils = new PageUtils(page);
        return R.ok().put("page", pageUtils);
    }
    /**
     * 列表
     */
    @RequestMapping("/listByMy7D") //7日内到期款；
    public R listByMy7D(@RequestParam Map<String, Object> params){
        String name = getUser().getName();
        IPage<BusiReportWjkEntity> page = busiReportWjkService.page(
                new Query<BusiReportWjkEntity>().getPage(params),
                new QueryWrapper<BusiReportWjkEntity>()
                        .lambda()
                        .eq(BusiReportWjkEntity::getType,6)
                        .lt(BusiReportWjkEntity::getYqdate,0)
                        .like(BusiReportWjkEntity::getUsername,name)
                        .orderByDesc(BusiReportWjkEntity::getQsdate)
        );
        PageUtils pageUtils = new PageUtils(page);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 列表
     */
    @RequestMapping("/listYqWjk") //逾期未交款；
    public R listYqWjk(@RequestParam Map<String, Object> params){
        String name = getUser().getName();
        params.put("mobilePhone",name);
        PageUtils pageUtils = busiReportWjkService.listYqWjk(params);
        return R.ok().put("page", pageUtils);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("erp:busireportwjk:info")
    public R info(@PathVariable("id") Integer id){
		BusiReportWjkEntity busiReportWjk = busiReportWjkService.getById(id);

        return R.ok().put("busiReportWjk", busiReportWjk);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("erp:busireportwjk:save")
    public R save(@RequestBody BusiReportWjkEntity busiReportWjk){
		busiReportWjkService.save(busiReportWjk);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("erp:busireportwjk:update")
    public R update(@RequestBody BusiReportWjkEntity busiReportWjk){
		busiReportWjkService.updateById(busiReportWjk);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("erp:busireportwjk:delete")
    public R delete(@RequestBody Integer[] ids){
		busiReportWjkService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
