package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.service.BusiCustomerService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author gale
 * @Date 2020/11/28
 * @Version 1.0
 * @Description
 **/
@RestController
@RequestMapping("busi/manager/busicustomer")
public class BusiManagerCustomerController {
  @Autowired
  private BusiCustomerService busiCustomerService;
  @Autowired
  private SysUserService sysUserService;

  /**
   * 列表
   */
  @RequestMapping("/list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = busiCustomerService.queryPage(params);

    return R.ok().put("page", page);
  }

  /**
   * 正常跟进客户分组列表
   */
  @RequestMapping("/groupNormalFollowList")
  public R groupNormalFollowList(@RequestParam Map<String, Object> params) {
    if (null == params.get("projectId")) {
      return R.error("请选择当前要查看的项目");
    } else {
      return R.ok().put("datas", sysUserService.queryNormalFollow(Long.valueOf(params.get("projectId").toString())));
    }
  }

  /**
   * 正常跟进客户列表
   */
  @RequestMapping("/normalFollowList")
  public R List(@RequestParam Map<String, Object> params) {
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.normalFollowPage(iPage, params.get("userId").toString());
    return R.ok().put("page", new PageUtils(iPage));
  }

  /**
   * 列表
   */
  @RequestMapping("/allList")
  public R allList(@RequestParam Map<String, Object> params) {
    PageUtils page = busiCustomerService.queryPage(params);

    return R.ok().put("page", page);
  }

  /**
   * 信息
   */
  @RequestMapping("/info/{id}")
  public R info(@PathVariable("id") Integer id) {
    BusiCustomerEntity busiCustomer = busiCustomerService.getById(id);

    return R.ok().put("busiCustomer", busiCustomer);
  }

  /**
   * 保存
   */
  @RequestMapping("/save")
  public R save(@RequestBody BusiCustomerEntity busiCustomer) {
    busiCustomerService.save(busiCustomer);

    return R.ok();
  }

  /**
   * 修改
   */
  @RequestMapping("/update")
  public R update(@RequestBody BusiCustomerEntity busiCustomer) {
    busiCustomerService.updateById(busiCustomer);

    return R.ok();
  }

  /**
   * 删除
   */
  @RequestMapping("/delete")
  @RequiresPermissions("busi:busicustomer:delete")
  public R delete(@RequestBody Integer[] ids) {
    busiCustomerService.removeByIds(Arrays.asList(ids));

    return R.ok();
  }

}
