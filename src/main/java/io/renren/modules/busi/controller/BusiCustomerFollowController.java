package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.BusiCustomerFollowEntity;
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
    public R list(@RequestBody Map<String, Object> params){
        List<Integer> projectIds = getProjectIds();
        PageUtils page = busiCustomerFollowService.listPage(params,projectIds);
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
    @SysLog("保存跟进记录")
    public R save(@RequestBody BusiCustomerFollowEntity busiCustomerFollow){
        SysUserEntity user = getUser();
        busiCustomerFollow.setCreateName(user.getName());
        busiCustomerFollow.setCreateTime(new Date());
        Date date = new Date();
        BusiCustomerEntity customerEntity = busiCustomerService.getById(busiCustomerFollow.getCustomerId());
        BusiCustomerEntity busiCustomerEntity = new BusiCustomerEntity();
        busiCustomerEntity.setId(busiCustomerFollow.getCustomerId());
        busiCustomerEntity.setUpdateTime(date);
        busiCustomerEntity.setFollowLast(busiCustomerFollow.getContent());
        busiCustomerEntity.setFollowMode(busiCustomerFollow.getMode());
        busiCustomerEntity.setFollowDate(date);
        busiCustomerEntity.setFollowNextDate(busiCustomerFollow.getNextDate());
        busiCustomerEntity.setFollowUserId(getUserId());
        busiCustomerFollowService.saveFollow(busiCustomerFollow, busiCustomerEntity);
        //是否将用户置为无效
        if(busiCustomerFollow.getInvalid() != null && busiCustomerFollow.getInvalid() == 1){
            BusiCustomerEntity updateCustomer = new BusiCustomerEntity();
            updateCustomer.setId(busiCustomerFollow.getCustomerId());
            updateCustomer.setInvalid(1);
            updateCustomer.setInvalidCause(busiCustomerFollow.getInvalidCause());
            busiCustomerService.updateById(updateCustomer);
        }
        //如果用户为无效用户则置为有效
        if(customerEntity.getInvalid() == 1){
            BusiCustomerEntity updateCustomer = new BusiCustomerEntity();
            updateCustomer.setId(busiCustomerFollow.getCustomerId());
            updateCustomer.setInvalid(0);
            updateCustomer.setInvalidCause(busiCustomerFollow.getInvalidCause());
            busiCustomerService.updateById(updateCustomer);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("busi:busicustomerfollow:update")
    @SysLog("修改跟进记录")
    public R update(@RequestBody BusiCustomerFollowEntity busiCustomerFollow){
		busiCustomerFollowService.updateById(busiCustomerFollow);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomerfollow:delete")
    @SysLog("删除跟进记录")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerFollowService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
