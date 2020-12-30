package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiHouseEntity;
import io.renren.modules.busi.entity.BusiHouseGroupEntity;
import io.renren.modules.busi.service.BusiHouseGroupService;
import io.renren.modules.busi.service.BusiHouseService;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-26 23:05:13
 */
@RestController
@RequestMapping("busi/busihousegroup")
public class BusiHouseGroupController extends AbstractController {
    @Autowired
    private BusiHouseGroupService busiHouseGroupService;
    @Autowired
    private BusiHouseService busiHouseService;


    /**
     * 列表
     */
    @RequestMapping("/listByProjectId/{pid}")
    public R listByProjectId(@PathVariable("pid") Integer pid){
        List<BusiHouseGroupEntity> list = busiHouseGroupService.list(new QueryWrapper<BusiHouseGroupEntity>().eq("parent_id", pid).eq("type", "栋"));
        if(getUser().getAppRole() == 2) {
            for (BusiHouseGroupEntity busiHouseGroupEntity : list) {
                Integer zs = busiHouseService.count(new QueryWrapper<BusiHouseEntity>().lambda().eq(BusiHouseEntity::getProjectId, busiHouseGroupEntity.getId()));
                QueryWrapper<BusiHouseEntity> busiHouseEntityQueryWrapper = new QueryWrapper<>();
                busiHouseEntityQueryWrapper.lambda().eq(BusiHouseEntity::getProjectId, busiHouseGroupEntity.getId());
                busiHouseEntityQueryWrapper.lambda().and(wrapper -> wrapper.ne(BusiHouseEntity::getStatus, "1").or().eq(BusiHouseEntity::getControl, 1));
                Integer ys = busiHouseService.count(busiHouseEntityQueryWrapper);

                busiHouseGroupEntity.setSaleSum(ys + "");
                busiHouseGroupEntity.setSum(zs + "");
            }
        }
        if(getUser().getAppRole() == 1) {
            for (BusiHouseGroupEntity busiHouseGroupEntity : list) {
                Integer zs = busiHouseService.count(new QueryWrapper<BusiHouseEntity>().lambda().eq(BusiHouseEntity::getProjectId, busiHouseGroupEntity.getId()));
                Integer ds = busiHouseService.count(new QueryWrapper<BusiHouseEntity>()
                        .lambda().eq(BusiHouseEntity::getProjectId, busiHouseGroupEntity.getId()).eq(BusiHouseEntity::getStatus,"1").ne(BusiHouseEntity::getControl,1));
                Integer xk = busiHouseService.count(new QueryWrapper<BusiHouseEntity>()
                        .lambda().eq(BusiHouseEntity::getProjectId, busiHouseGroupEntity.getId()).eq(BusiHouseEntity::getStatus,"10")
                        .and(wrapper -> wrapper.ne(BusiHouseEntity::getStatus, "1").or().eq(BusiHouseEntity::getControl, 1)));

                Integer rg = busiHouseService.count(new QueryWrapper<BusiHouseEntity>()
                        .lambda().eq(BusiHouseEntity::getProjectId, busiHouseGroupEntity.getId()).eq(BusiHouseEntity::getStatus,"20"));
                Integer qy = busiHouseService.count(new QueryWrapper<BusiHouseEntity>()
                        .lambda().eq(BusiHouseEntity::getProjectId, busiHouseGroupEntity.getId()).eq(BusiHouseEntity::getStatus,"30"));
                busiHouseGroupEntity.setZs(zs);
                busiHouseGroupEntity.setDs(ds);
                busiHouseGroupEntity.setXk(xk);
                busiHouseGroupEntity.setRg(rg);
                busiHouseGroupEntity.setQy(qy);
            }
        }
        return R.ok().put("list", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/listAdminByProjectId/{pid}")
    public R listAdminByProjectId(@PathVariable("pid") Integer pid){
        List<BusiHouseGroupEntity> list = busiHouseGroupService.list(new QueryWrapper<BusiHouseGroupEntity>().eq("parent_id", pid).eq("type", "栋"));
        return R.ok().put("list", list);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiHouseGroupService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busihousegroup:info")
    public R info(@PathVariable("id") Integer id){
		BusiHouseGroupEntity busiHouseGroup = busiHouseGroupService.getById(id);

        return R.ok().put("busiHouseGroup", busiHouseGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("busi:busihousegroup:save")
    public R save(@RequestBody BusiHouseGroupEntity busiHouseGroup){
		busiHouseGroupService.save(busiHouseGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busihousegroup:update")
    public R update(@RequestBody BusiHouseGroupEntity busiHouseGroup){
		busiHouseGroupService.updateById(busiHouseGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busihousegroup:delete")
    public R delete(@RequestBody Integer[] ids){
		busiHouseGroupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
