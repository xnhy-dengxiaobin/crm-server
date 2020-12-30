package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerOrderEntity;
import io.renren.modules.busi.entity.BusiOrderEntity;
import io.renren.modules.busi.service.BusiCustomerOrderService;
import io.renren.modules.busi.service.BusiOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-29 00:08:46
 */
@RestController
@RequestMapping("busi/busiorder")
public class BusiOrderController {
    @Autowired
    private BusiOrderService busiOrderService;
    @Autowired
    private BusiCustomerOrderService customerOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = busiOrderService.listPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 修改
     */
    @RequestMapping("/relevance")
    public R relevance(@RequestBody Map<String,Object> busiOrder){
        Integer orderId = Integer.parseInt(busiOrder.get("orderId").toString());
        List<Integer> customerList = (List<Integer>)busiOrder.get("customerList");
        if (customerList == null || customerList.size() == 0){
            throw new RRException("关联客户不能为空");
        }
        List<BusiCustomerOrderEntity> list = new ArrayList<>();
        customerOrderService.remove(new QueryWrapper<BusiCustomerOrderEntity>().lambda().eq(BusiCustomerOrderEntity::getOrderId,orderId));
        for (Integer customerId : customerList) {
            BusiCustomerOrderEntity busiCustomerOrderEntity = new BusiCustomerOrderEntity();
            busiCustomerOrderEntity.setOrderId(orderId);
            busiCustomerOrderEntity.setCustomerId(customerId);
            list.add(busiCustomerOrderEntity);
        }
        customerOrderService.saveBatch(list);
        return R.ok();
    }


  /**
   * 信息
   */
  @RequestMapping("/info/{id}")
  @RequiresPermissions("busi:busiorder:info")
  public R info(@PathVariable("id") Integer id) {
    BusiOrderEntity busiOrder = busiOrderService.getById(id);

    return R.ok().put("busiOrder", busiOrder);
  }

  /**
   * 保存
   */
  @RequestMapping("/save")
  @RequiresPermissions("busi:busiorder:save")
  public R save(@RequestBody BusiOrderEntity busiOrder) {
    busiOrderService.save(busiOrder);

    return R.ok();
  }

  /**
   * 修改
   */
  @RequestMapping("/update")
  @RequiresPermissions("busi:busiorder:update")
  public R update(@RequestBody BusiOrderEntity busiOrder) {
    busiOrderService.updateById(busiOrder);

    return R.ok();
  }

  /**
   * 删除
   */
  @RequestMapping("/delete")
  @RequiresPermissions("busi:busiorder:delete")
  public R delete(@RequestBody Integer[] ids) {
    busiOrderService.removeByIds(Arrays.asList(ids));

    return R.ok();
  }

}
