package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiTeamsEntity;
import io.renren.modules.busi.service.BusiTeamsService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-25 10:23:57
 */
@RestController
@RequestMapping("busi/busiteams")
public class BusiTeamsController {
    @Autowired
    private BusiTeamsService busiTeamsService;

    @Autowired
    private SysUserService sysUserService;

    /**
     *
     */
    @RequestMapping("/userList")
    public R userList(@RequestParam Map<String, Object> params){
        List<SysUserEntity> list = sysUserService.list(new QueryWrapper<SysUserEntity>()
                .lambda().eq(SysUserEntity::getTeamId, params.get("teamId")));
        return R.ok().put("list", list);
    }

    /**
     *
     */
    @RequestMapping("/userListSelect")
    public R userListSelect(@RequestParam Map<String, Object> params){
        List<SysUserEntity> listAll = new ArrayList<>();
        List<SysUserEntity> list1 = sysUserService.list(new QueryWrapper<SysUserEntity>()
                .lambda().isNull(SysUserEntity::getTeamId));
        listAll.addAll(list1);
        List<SysUserEntity> list2 = sysUserService.list(new QueryWrapper<SysUserEntity>()
                .lambda().ne(SysUserEntity::getTeamId, params.get("teamId")).orderByDesc(SysUserEntity::getTeamId));
        listAll.addAll(list2);
        return R.ok().put("list", listAll);
    }

    /**
     * 保存
     */
    @RequestMapping("/bidingUser")
    public R bidingUser(@RequestBody Map<String,Object> param){
        List<Integer> ids = (List)param.get("ids");
        Long teamId = Long.parseLong(param.get("teamId").toString());
        BusiTeamsEntity byId = busiTeamsService.getById(teamId);
        for (Integer id : ids) {
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setUserId(Long.parseLong(id+""));
            sysUserEntity.setTeamId(teamId);
            sysUserEntity.setTeamName(byId.getTeamName());
            sysUserService.updateById(sysUserEntity);
        }
        return R.ok();
    }

    /**
     * 取消
     */
    @RequestMapping("/delBidingUse/{userId}")
    public R delBidingUser(@PathVariable("userId") Integer userId){
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserId(Long.parseLong(userId+""));
        sysUserEntity.setTeamId(null);
        sysUserEntity.setTeamName(null);
        sysUserService.updateTeam(sysUserEntity);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busiteams:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiTeamsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busiteams:info")
    public R info(@PathVariable("id") Integer id){
		BusiTeamsEntity busiTeams = busiTeamsService.getById(id);

        return R.ok().put("busiTeams", busiTeams);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BusiTeamsEntity busiTeams){
		busiTeamsService.save(busiTeams);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busiteams:update")
    public R update(@RequestBody BusiTeamsEntity busiTeams){
		busiTeamsService.updateById(busiTeams);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busiteams:delete")
    public R delete(@RequestBody Integer[] ids){
		busiTeamsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
