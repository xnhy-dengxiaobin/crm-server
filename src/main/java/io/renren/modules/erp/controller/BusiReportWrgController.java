package io.renren.modules.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.erp.entity.BusiReportWrgEntity;
import io.renren.modules.erp.service.BusiReportWrgService;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 未认购
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-06 17:24:58
 */
@RestController
@RequestMapping("erp/busireportwrg")
public class BusiReportWrgController extends AbstractController {
    @Autowired
    private BusiReportWrgService busiReportWrgService;

    /**
     * 列表
     */
    @RequestMapping("/listByMy")
    public R listByMy(@RequestParam Map<String, Object> params){
        String name = getUser().getName();
        IPage<BusiReportWrgEntity> page = busiReportWrgService.page(
                new Query<BusiReportWrgEntity>().getPage(params),
                new QueryWrapper<BusiReportWrgEntity>()
                        .lambda()
                        .gt(BusiReportWrgEntity::getYqdate,0)
                        .like(BusiReportWrgEntity::getUsername,name)
                        .orderByDesc(BusiReportWrgEntity::getQsdate)
        );
        PageUtils pageUtils = new PageUtils(page);
        return R.ok().put("page", pageUtils);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("erp:busireportwrg:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiReportWrgService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("erp:busireportwrg:info")
    public R info(@PathVariable("id") Integer id){
		BusiReportWrgEntity busiReportWrg = busiReportWrgService.getById(id);

        return R.ok().put("busiReportWrg", busiReportWrg);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("erp:busireportwrg:save")
    public R save(@RequestBody BusiReportWrgEntity busiReportWrg){
		busiReportWrgService.save(busiReportWrg);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("erp:busireportwrg:update")
    public R update(@RequestBody BusiReportWrgEntity busiReportWrg){
		busiReportWrgService.updateById(busiReportWrg);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("erp:busireportwrg:delete")
    public R delete(@RequestBody Integer[] ids){
		busiReportWrgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
