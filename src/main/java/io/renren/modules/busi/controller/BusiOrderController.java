package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiContractEntity;
import io.renren.modules.busi.entity.BusiCustomerOrderEntity;
import io.renren.modules.busi.entity.BusiOrderEntity;
import io.renren.modules.busi.entity.BusiTradeEntity;
import io.renren.modules.busi.service.*;
import io.renren.modules.busi.vo.BusiOrderVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private BusiTradeService tradeService;
    @Autowired
    private BusiCustomerService customerService;
    @Autowired
    private BusiContractService busiContractService;
    /**
     * 催收列表
     */
    @RequestMapping("/dataPreparedList")
    @RequiresPermissions("busi:order:prompt:list")
    public R prompt(@RequestBody Map<String, Object> params) {
      IPage<BusiOrderVO> page = new Query<BusiOrderVO>().getPage(params);
      busiOrderService.promptPage(page,params.get("condition")==null?"":params.get("condition").toString());
      return R.ok().put("page", new PageUtils(page));
    }

    /**
     *
     */
    @RequestMapping("/infoByRoomId/{id}/{status}")
    public R infoByRoomId(@PathVariable("id") String id,@PathVariable("status") Integer status){
        if (status == 3){
            BusiTradeEntity one = tradeService.getOne(new QueryWrapper<BusiTradeEntity>().lambda().eq(BusiTradeEntity::getRoomguid, id));
            return R.ok().put("rs", one);
        }else if(status == 4){
            BusiContractEntity one = busiContractService.getOne(new QueryWrapper<BusiContractEntity>().lambda().eq(BusiContractEntity::getRoomguid, id));
            return R.ok().put("rs", one);
        }
        return null;
//        List<BusiTradeEntity> list1 = tradeService.list(new QueryWrapper<BusiTradeEntity>()
//                .lambda()
//                .eq(BusiTradeEntity::getRoomguid, id));
////                .eq(BusiTradeEntity::getRoomStatus, status));
//
//        BusiTradeEntity busiTradeEntity = list1.get(0);
//        List<BusiOrderEntity> list = busiOrderService.list(new QueryWrapper<BusiOrderEntity>()
//                .lambda().eq(BusiOrderEntity::getRoomId, id)
//                .eq(BusiOrderEntity::getId,busiTradeEntity.getOrderId()));
//        BusiOrderEntity busiOrderEntity = list.get(0);
//        List<BusiCustomerOrderEntity> list2 = customerOrderService.list(new QueryWrapper<BusiCustomerOrderEntity>().lambda().eq(BusiCustomerOrderEntity::getOrderId, busiOrderEntity.getId()));
//        List<Integer> ids = list2.stream().map(BusiCustomerOrderEntity::getCustomerId).collect(Collectors.toList());
//        List<BusiCustomerEntity> list3 = customerService.list(new QueryWrapper<BusiCustomerEntity>().lambda().in(BusiCustomerEntity::getId, ids));
//        return R.ok().put("trade", busiTradeEntity).put("order",busiOrderEntity).put("list",list3);
    }
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
  /**
   * 删除
   */
  @RequestMapping("/complete")
  @RequiresPermissions("busi:order:prompt:confirm")
  public R complete(@RequestBody Integer[] ids) {
    busiOrderService.update(new UpdateWrapper<BusiOrderEntity>().lambda().set(BusiOrderEntity::getDataPrepared,0).in(BusiOrderEntity::getId,ids));

    return R.ok();
  }

}
