package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.busi.service.BusiProjectService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 项目
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@RestController
@RequestMapping("busi/busiproject")
public class BusiProjectController extends AbstractController {
    @Autowired
    private BusiProjectService busiProjectService;


    /**
     * 列表
     */
    @RequestMapping("/listByParentId/{pid}")
    public R listByParentId(@PathVariable("pid") Integer pid){
        List<BusiProjectEntity> rsList = busiProjectService.queryGroupList(getUserId());

        return R.ok().put("list", rsList);
    }
    /**
     * 列表
     */
    @RequestMapping("/list2000ByParentId")
    public R list2000ByParentId(){
        List<Integer> projectIds = getProjectIds();
        List<BusiProjectEntity> rsList = new ArrayList<>();
        for (Integer projectId : projectIds) {
            List<BusiProjectEntity> list = busiProjectService.list(new QueryWrapper<BusiProjectEntity>().lambda().eq(BusiProjectEntity::getParentId, projectId));
            rsList.addAll(list);
        }
        return R.ok().put("list", rsList);
    }
    /**
     * 列表
     */
    @RequestMapping("/listParent")
    public R listParent(@RequestBody(required = false) SysUserEntity user){
//        List<BusiProjectEntity> rsUsers = busiProjectService.list(new QueryWrapper<BusiProjectEntity>().eq("user_id", user.getUserId()));
//        String userSring = "";
//        for(BusiProjectEntity rsUser:rsUsers){
//            if(userSring.equals("")){
//                userSring = rsUser.getId().toString();
//            }else{
//                userSring = userSring + "," + rsUser.getId().toString();
//            }
//        }
        List<BusiProjectEntity> rsList = busiProjectService.queryGroupList(getUserId());
        return R.ok().put("list", rsList);
    }

    /**
     * 列表
     */
    @RequestMapping("/listGroup")
    public R listGroup(){
        List<BusiProjectEntity> rsList = busiProjectService.list(new QueryWrapper<BusiProjectEntity>().eq("level",2));
        return R.ok().put("list", rsList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busiproject:list")
    public List<BusiProjectEntity> list(@RequestParam Map<String, Object> params){
      List<BusiProjectEntity> list = busiProjectService.list();
      for(BusiProjectEntity projectEntity : list){
        BusiProjectEntity parentProjectEntity = busiProjectService.getById(projectEntity.getParentId());
        if(parentProjectEntity != null){
          projectEntity.setParentName(parentProjectEntity.getName());
        }
      }
      return list;
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
  /**
   * 删除
   */
  @SysLog("删除项目")
  @PostMapping("/delete/{projectId}")
  @RequiresPermissions("busi:busiproject:delete")
  public R delete(@PathVariable("projectId") long projectId){
    if(projectId < 1){
      return R.error("参数错误，请检查");
    }
    //判断是否有子菜单或按钮
    List<BusiProjectEntity> projectList = busiProjectService.list(new QueryWrapper<BusiProjectEntity>().eq("parent_id", projectId));
    if(projectList.size() > 0){
      return R.error("请先删除子项目");
    }
    busiProjectService.removeById(projectId);
    return R.ok();
  }

}
