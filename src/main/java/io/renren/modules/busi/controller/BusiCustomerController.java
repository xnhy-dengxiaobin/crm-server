package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.service.BusiCustomerService;
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
        PageUtils page = busiCustomerService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busiCustomerService.queryPage(params);

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
    public R update(@RequestBody BusiCustomerEntity busiCustomer){
        busiCustomerService.perfect(busiCustomer);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("busi:busicustomer:delete")
    public R delete(@RequestBody Integer[] ids){
		busiCustomerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
