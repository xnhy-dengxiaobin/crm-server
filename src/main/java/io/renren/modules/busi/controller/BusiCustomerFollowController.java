package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.BusiCustomerFollowEntity;
import io.renren.modules.busi.entity.BusiCustomerProjectEntity;
import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.busi.service.BusiCustomerFollowService;
import io.renren.modules.busi.service.BusiCustomerProjectService;
import io.renren.modules.busi.service.BusiCustomerService;
import io.renren.modules.busi.service.BusiProjectService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
@RestController
@RequestMapping("busi/busicustomerfollow")
public class BusiCustomerFollowController extends AbstractController {
    @Autowired
    private BusiCustomerFollowService busiCustomerFollowService;
    @Autowired
    private BusiCustomerService busiCustomerService;
    @Autowired
    private BusiProjectService busiProjectService;
    @Autowired
    private BusiCustomerProjectService busiCustomerProjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("busi:busicustomerfollow:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerFollowService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/listByCid")
    public R listByCid(@RequestParam Map<String, String> params){
        Integer cid = Integer.parseInt(params.get("cid"));
        List<BusiCustomerFollowEntity> rs = busiCustomerFollowService.list(new QueryWrapper<BusiCustomerFollowEntity>()
                .eq("customer_id", cid)
                .orderByDesc("create_time"));
        return R.ok().put("list", rs);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("busi:busicustomerfollow:info")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerFollowEntity busiCustomerFollow = busiCustomerFollowService.getById(id);

        return R.ok().put("busiCustomerFollow", busiCustomerFollow);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BusiCustomerFollowEntity busiCustomerFollow){
        SysUserEntity user = getUser();
        busiCustomerFollow.setCreateName(user.getUsername());
        busiCustomerFollow.setCreateTime(new Date());
        busiCustomerFollowService.save(busiCustomerFollow);
        BusiCustomerEntity busiCustomerEntity = new BusiCustomerEntity();
        busiCustomerEntity.setId(busiCustomerFollow.getCustomerId());
        busiCustomerEntity.setUpdateTime(new Date());
        busiCustomerEntity.setFollowLast(busiCustomerFollow.getContent());
        busiCustomerEntity.setFollowMode(busiCustomerFollow.getMode());
        busiCustomerService.updateById(busiCustomerEntity);
        if(busiCustomerFollow.getProjectId() != null){
            BusiProjectEntity byId = busiProjectService.getById(busiCustomerFollow.getProjectId());
            BusiCustomerProjectEntity entity = new BusiCustomerProjectEntity();
            entity.setCustomerId(busiCustomerFollow.getCustomerId());
            entity.setProjectId(byId.getId());
            entity.setProject(byId.getName());
            busiCustomerProjectService.save(entity);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicustomerfollow:update")
    public R update(@RequestBody BusiCustomerFollowEntity busiCustomerFollow){
		busiCustomerFollowService.updateById(busiCustomerFollow);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomerfollow:delete")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerFollowService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
