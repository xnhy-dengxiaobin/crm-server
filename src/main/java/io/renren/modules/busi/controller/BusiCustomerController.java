package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.service.BusiCustomerService;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 *
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
@RestController
@RequestMapping("busi/busicustomer")
public class BusiCustomerController extends AbstractController {
    @Autowired
    private BusiCustomerService busiCustomerService;

    /**
     * 根据完整电话号码或者后四位查询
     */
    @RequestMapping("/listByPhone")
    public R listByPhone(@RequestBody Map<String, Object> params){
        params.put("userId", getUserId());
        List<BusiCustomerEntity> customers = busiCustomerService.queryByPhone(params);

        return R.ok().put("customers", customers);
    }


    /**
     * 列表
     */
    @RequestMapping("/myList")
    public R myList(@RequestParam Map<String, Object> params){
        params.put("matchUserId",getUserId() +"");
        params.put("desc","follow_date");
//        params.put("projectId",getProjectId());
        PageUtils page = busiCustomerService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/myNewList")
    public R myNewList(@RequestParam Map<String, Object> params){
        params.put("matchUserId",getUserId() +"");
        params.put("followUserId",getUserId());
//        params.put("projectId",getProjectId());
        PageUtils page = busiCustomerService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/followList")
    public R followList(@RequestParam Map<String, Object> params){
        QueryWrapper<BusiCustomerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                    .le(BusiCustomerEntity::getFollowNextDate,getEndTime())
                    .eq(BusiCustomerEntity::getMatchUserId,getUserId())
//                    .eq(BusiCustomerEntity::getProjectId,getProjectId())
                    .orderByDesc(BusiCustomerEntity::getFollowNextDate);
        IPage<BusiCustomerEntity> page = busiCustomerService.page(
                new Query<BusiCustomerEntity>().getPage(params),
                queryWrapper
        );
        return R.ok().put("page", new PageUtils(page));
    }

    private Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = busiCustomerService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listSourceCus")
    public R listSourceCus(@RequestBody Map<String, Object> params){
        params.put("userId", getUserId());
        PageUtils page = busiCustomerService.querySourceCus(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/allList")
    public R allList(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		BusiCustomerEntity busiCustomer = busiCustomerService.getById(id);

        return R.ok().put("busiCustomer", busiCustomer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @SysLog("保存客户")
    public R save(@RequestBody BusiCustomerEntity busiCustomer){
        BusiCustomerEntity cus = busiCustomerService.getBaseMapper().selectOne(new QueryWrapper<BusiCustomerEntity>().eq("mobile_phone", busiCustomer.getMobilePhone()));
        if(null != cus && cus.getId() > 0){
            return R.error("该手机号码已经存在, 不是新客户");
        }

		busiCustomerService.save(busiCustomer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @SysLog("修改客户")
    public R update(@RequestBody BusiCustomerEntity busiCustomer){
        if (busiCustomer.getId() != null) {
            BusiCustomerEntity byId = busiCustomerService.getById(busiCustomer.getId());
            if(!byId.getMatchUserId().equals(getUserId()+"")){
                throw new RRException("客户归宿异常");
            }
            busiCustomer.setMatchUserId(getUserId()+"");
            busiCustomerService.perfect(busiCustomer);
        }else {
            BusiCustomerEntity one = busiCustomerService.getOne(new QueryWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getMobilePhone, busiCustomer.getMobilePhone()).ne(BusiCustomerEntity::getInvalid, 1));
            if(null != one && one.getId() > 0){
                return R.error("该手机号码已经存在, 不是新客户");
            }
            busiCustomer.setSource("来电");
            busiCustomer.setMatchUserId(getUserId()+"");
            busiCustomer.setMatchUserTime(new Date());
            busiCustomer.setProjectId(getProjectId());
            busiCustomer.setStatus(1);
            busiCustomerService.save(busiCustomer);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomer:delete")
    @SysLog("删除客户")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
