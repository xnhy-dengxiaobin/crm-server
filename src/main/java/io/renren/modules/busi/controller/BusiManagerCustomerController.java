package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.BusiCustomerRoamEntity;
import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.service.BusiCustomerFollowService;
import io.renren.modules.busi.service.BusiCustomerRoamService;
import io.renren.modules.busi.service.BusiCustomerService;
import io.renren.modules.busi.service.ReceptionService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Author gale
 * @Date 2020/11/28
 * @Version 1.0
 * @Description
 **/
@RestController
@RequestMapping("busi/manager/busicustomer")
public class BusiManagerCustomerController extends AbstractController {
  @Autowired
  private BusiCustomerService busiCustomerService;
  @Autowired
  private SysUserService sysUserService;
  @Autowired
  private ReceptionService receptionService;
  @Autowired
  private BusiCustomerFollowService busiCustomerFollowService;
  @Autowired
  private BusiCustomerRoamService busiCustomerRoamService;

  /**
   * 列表
   */
  @RequestMapping("/list")
  public R list(@RequestParam Map<String, Object> params) {
    PageUtils page = busiCustomerService.queryPage(params);

    return R.ok().put("page", page);
  }

  /**
   * 分析统计
   *
   * @return
   */
  @RequestMapping("/statistics")
  public R statistics(@RequestParam Map<String, Object> params) {
    Object projectId = params.get("projectId");
    if (null == projectId) {
      return R.error("请选择当前要查看的项目");
    } else {
      return R.ok().put("newClient", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getProjectId, projectId).ge(BusiCustomerEntity::getCreateTime, LocalDate.now())))
        .put("flowClient", busiCustomerFollowService.toDayCount(projectId.toString()))
        .put("receptionNewClient", receptionService.count(new QueryWrapper<ReceptionEntity>().lambda().eq(ReceptionEntity::getProjectId, projectId).ge(ReceptionEntity::getIsNew, 1)))
        .put("receptionOldClient", receptionService.count(new QueryWrapper<ReceptionEntity>().lambda().eq(ReceptionEntity::getProjectId, projectId).ge(ReceptionEntity::getIsNew, 0)));
    }
  }

  /**
   * 客户分组统计
   */
  @RequestMapping("/groupList")
  public R groupList(@RequestParam Map<String, Object> params) {
    if (null == params.get("projectId")) {
      return R.error("请选择当前要查看的项目");
    } else {
      return R.ok()
        .put("timeoutCount", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>().lambda().le(BusiCustomerEntity::getFollowNextDate, new Date()).eq(BusiCustomerEntity::getProjectId, params.get("projectId")).eq(BusiCustomerEntity::getStatus, 1).isNotNull(BusiCustomerEntity::getMatchUserId)))
        .put("normalCount", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>().lambda().gt(BusiCustomerEntity::getFollowNextDate, new Date()).eq(BusiCustomerEntity::getProjectId, params.get("projectId")).eq(BusiCustomerEntity::getStatus, 1).isNotNull(BusiCustomerEntity::getMatchUserId)))
        .put("recoveryCount", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getProjectId, params.get("projectId")).isNull(BusiCustomerEntity::getMatchUserId).eq(BusiCustomerEntity::getStatus, 2)));
    }
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
   * 逾期客户
   */
  @RequestMapping("/groupTimeoutList")
  public R groupTimeoutList(@RequestParam Map<String, Object> params) {
    if (null == params.get("projectId")) {
      return R.error("请选择当前要查看的项目");
    } else {
      return R.ok().put("datas", sysUserService.queryTimeoutList(Long.valueOf(params.get("projectId").toString())));
    }
  }

  /**
   * 正常跟进客户列表
   */
  @RequestMapping("/normalFollowList")
  public R List(@RequestParam Map<String, Object> params) {
    if (params.get("userId") == null || params.get("projectId") == null) {
      return R.error("参数异常");
    }
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.normalFollowPage(iPage, params.get("userId").toString(), params.get("projectId").toString());
    return R.ok().put("page", new PageUtils(iPage));
  }

  /**
   * 逾期客户列表
   */
  @RequestMapping("/timeoutList")
  public R timeoutList(@RequestParam Map<String, Object> params) {
    if (params.get("userId") == null || params.get("projectId") == null) {
      return R.error("参数异常");
    }
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.timeoutPage(iPage, params.get("userId").toString(), params.get("projectId").toString(),null);
    return R.ok().put("page", new PageUtils(iPage));
  }
  /**
   * 逾期客户列表
   */
  @RequestMapping("/allTimeoutList")
  public R allTimeoutList(@RequestParam Map<String, Object> params) {
    String keywords = ParamResolvor.getString(params, "keyword");
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.timeoutPage(iPage, null, null,keywords);
    return R.ok().put("page", new PageUtils(iPage));
  }

  /**
   * 公共客户
   */
  @RequestMapping("/publicList")
  public R publicList(@RequestParam Map<String, Object> params) {
//    if (params.get("projectId") == null) {
//      return R.error("参数异常");
//    }
    String keywords = ParamResolvor.getString(params, "keyword");
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.publicPage(iPage, params.get("projectId")==null?null:params.get("projectId").toString(),keywords);
    return R.ok().put("page", new PageUtils(iPage));
  }

  /**
   * 垃圾箱
   */
  @RequestMapping("/rubbishList")
  public R rubbishList(@RequestParam Map<String, Object> params) {
    if (params.get("projectId") == null) {
      return R.error("参数异常");
    }
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.publicPage(iPage, params.get("projectId").toString(),null);
    return R.ok().put("page", new PageUtils(iPage));
  }

  /**
   * 垃圾箱
   */
  @RequestMapping("/rubbish")
  public R rubbish(@RequestParam Map<String, Object> params) {
    Object obj = params.get("ids");
    String idstr = obj == null ? "" : obj.toString();
    String[] ids = idstr.split(",");
    if (ids.length < 1) {
      return R.ok();
    } else {
      for (String id : ids) {
        BusiCustomerRoamEntity roam = new BusiCustomerRoamEntity();
        roam.setCreateTime(new Date());
        roam.setCustomerId(Integer.parseInt(id));
        roam.setRemark("垃圾箱，被" + getUser().getUsername() + "扔进垃圾箱");
        busiCustomerRoamService.save(roam);
        busiCustomerService.update(new UpdateWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getId, id)
          .set(BusiCustomerEntity::getStatus, 3)
        );
      }
    }
    return R.ok();
  }

  /**
   * 回收客户
   */
  @PostMapping("/recovery")
  @SysLog("回收客户")
  public R recovery(@RequestBody String[] ids) {
    if (ids.length < 1) {
      return R.ok();
    } else {
      for (String id : ids) {
        if(StringUtils.isEmpty(id)){
          continue;
        }
        BusiCustomerRoamEntity roam = new BusiCustomerRoamEntity();
        roam.setCreateTime(new Date());
        roam.setCustomerId(Integer.parseInt(id));
        roam.setRemark("回收，被" + getUser().getUsername() + "回收");
        busiCustomerRoamService.save(roam);
        BusiCustomerEntity entity = busiCustomerService.getById(id);
        if (entity != null && !StringUtils.isEmpty(entity.getMatchUserId())) {
          SysUserEntity sysUserEntity = sysUserService.getById(entity.getMatchUserId());
          if (sysUserEntity != null) {
            busiCustomerService.update(new UpdateWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getId, id).set(BusiCustomerEntity::getOldMatchUserId, sysUserEntity.getUserId())
              .set(BusiCustomerEntity::getOldMatchUserName, sysUserEntity.getUsername()).set(BusiCustomerEntity::getStatus, 2).set(BusiCustomerEntity::getMatchUserId, null)
            );
          }
        }
      }
    }
    return R.ok();
  }

  /**
   * 分配客户
   */
  @RequestMapping("/share")
  public R share(@RequestParam Map<String, Object> params) {
    Object userIdObj = params.get("userIds");
    Object customerIdObj = params.get("customerIds");
    String[] userIds = userIdObj == null ? new String[]{} : userIdObj.toString().split(",");
    String[] customerIds = customerIdObj == null ? new String[]{} : customerIdObj.toString().split(",");
    if (userIds.length < 1 || customerIds.length < 1) {
      return R.ok();
    } else {
      int i = 0;
      for (String customerId : customerIds) {
        if (i + 1 == userIds.length) {
          i = 0;
        }
        String userId = userIds[i];

        BusiCustomerRoamEntity roam = new BusiCustomerRoamEntity();
        roam.setCreateTime(new Date());
        roam.setCustomerId(Integer.parseInt(customerId));

        BusiCustomerEntity entity = busiCustomerService.getById(customerId);
        SysUserEntity sysUserEntity = sysUserService.getById(entity.getMatchUserId());
        roam.setUserId(Integer.parseInt(userId));
        roam.setRemark("分配，被" + getUser().getUsername() + "分配至" + sysUserService.getById(userId).getUsername());
        busiCustomerRoamService.save(roam);
        if (sysUserEntity == null) {
          sysUserEntity = new SysUserEntity();
        }
        busiCustomerService.update(new UpdateWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getId, customerId).set(BusiCustomerEntity::getOldMatchUserId, sysUserEntity.getUserId())
          .set(BusiCustomerEntity::getOldMatchUserName, sysUserEntity.getUsername()).set(BusiCustomerEntity::getStatus, 1).set(BusiCustomerEntity::getMatchUserId, userId).set(BusiCustomerEntity::getMatchUserTime,new Date())
        );

        i++;
      }
    }
    return R.ok();
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
