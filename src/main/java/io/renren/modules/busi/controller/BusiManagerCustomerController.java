package io.renren.modules.busi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.busi.entity.*;
import io.renren.modules.busi.service.*;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
  @Autowired
  private BusiUserProjectService busiUserProjectService;
  @Autowired
  private BusiBookingService busiBookingService;



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
      return R.ok().put("newClient", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>()
        .lambda().eq(BusiCustomerEntity::getProjectId, projectId)
        .ge(BusiCustomerEntity::getCreateTime, LocalDate.now())))

        .put("flowClient", busiCustomerFollowService.toDayCount(projectId.toString()))

        .put("receptionNewClient", receptionService.count(new QueryWrapper<ReceptionEntity>()
          .lambda().eq(ReceptionEntity::getProjectId, projectId)
          .ge(ReceptionEntity::getIsNew, 1)))

        .put("receptionOldClient", receptionService.count(new QueryWrapper<ReceptionEntity>()
          .lambda().eq(ReceptionEntity::getProjectId, projectId).
            ge(ReceptionEntity::getIsNew, 0)));
    }
  }

  /**
   * 分析统计
   *
   * @return
   */
  @RequestMapping("/statisticsSource")
  public R statisticsSource(@RequestParam Map<String, Object> params) {
    Object projectId = params.get("projectId");
    if (null == projectId) {
      return R.error("请选择当前要查看的项目");
    }
    String[] ids = projectId.toString().split(",");
    Object endDate = params.get("endDate");
    Object unit = params.get("unit");
    if (unit.toString().equals("day")) { //统计天
      return dayConut(ids, endDate, 2);
    } else if (unit.toString().equals("week")) { //统计周
      return weekCountS(ids, endDate);
    } else if (unit.toString().equals("month")) { //统计月
      return monthConut(ids, endDate, 2);
    } else if (unit.toString().equals("year")) {//统计年
      return yearConut(ids, endDate, 2);
    }
    return null;
  }

  /**
   * 分析统计
   *
   * @return
   */
  @RequestMapping("/statisticsCom")
  public R statisticsCom(@RequestParam Map<String, Object> params) {
    Object projectId = params.get("projectId");
    if (null == projectId) {
      return R.error("请选择当前要查看的项目");
    }
    String[] ids = projectId.toString().split(",");
    Object endDate = params.get("endDate");
    Object unit = params.get("unit");
    if (unit.toString().equals("day")) { //统计天
      return dayConut(ids, endDate, 1);
    } else if (unit.toString().equals("week")) { //统计周
      return weekConut(ids, endDate);
    } else if (unit.toString().equals("month")) { //统计月
      return monthConut(ids, endDate, 1);
    } else if (unit.toString().equals("year")) {//统计年
      return yearConut(ids, endDate, 1);
    }
    return null;
  }

  private R yearConut(String[] projectIds, Object endDate, int t) {
    if (endDate == null || endDate.equals("")) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String format = sdf.format(new Date());
      endDate = format;
    }
    List<Map> maps;
    if (t == 1) {
      maps = receptionService.groupByDateCountYear(endDate.toString(), projectIds);
    } else {
      maps = busiCustomerService.groupByDateCountYear(endDate.toString(), projectIds);
    }
    return R.ok().put("rs", maps);
  }

  private R monthConut(String[] projectIds, Object endDate, int t) {
    if (endDate == null || endDate.equals("")) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String format = sdf.format(new Date());
      endDate = format;
    }
    List<Map> maps;
    if (t == 1) {
      maps = receptionService.groupByDateCountMonth(endDate.toString(), projectIds);
    } else {
      maps = busiCustomerService.groupByDateCountMonth(endDate.toString(), projectIds);
    }
    return R.ok().put("rs", maps);
  }

  private R weekCountS(String[] projectIds, Object endDate) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String paramDate;
    if (endDate != null && !endDate.equals("")) {
      paramDate = endDate.toString();
    } else {
      Date date = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setFirstDayOfWeek(Calendar.SUNDAY);//设置星期一为一周开始的第一天
      calendar.setMinimalDaysInFirstWeek(4);//可以不用设置
      Integer weekNum;
      calendar.setTimeInMillis(date.getTime());//时间戳
      weekNum = calendar.get(Calendar.WEEK_OF_YEAR);//获得当前日期属于今年的第几周
      SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
      String format = simpleDateFormat2.format(date);
      int yyyy = Integer.parseInt(format);
      //获得指定年的第几周的结束日期
      calendar.setWeekDate(yyyy, weekNum, 1);
      Date endtime = calendar.getTime();
      paramDate = simpleDateFormat.format(endtime);
    }
    List<Map> maps = busiCustomerService.groupByDateCountWeek(paramDate, projectIds);
    return R.ok().put("rs", maps);
  }

  private R weekConut(String[] projectIds, Object endDate) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    if (endDate != null && !endDate.equals("")) {
      try {
        date = simpleDateFormat.parse(endDate.toString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
    } else {
      date = new Date();
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为一周开始的第一天
    calendar.setMinimalDaysInFirstWeek(4);//可以不用设置
    Integer weekNum;
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
    String format = simpleDateFormat2.format(date);
    SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MM/dd");
    int yyyy = Integer.parseInt(format);
    calendar.setTimeInMillis(date.getTime());//时间戳
    weekNum = calendar.get(Calendar.WEEK_OF_YEAR);//获得当前日期属于今年的第几周
    System.out.println("第几年：" + weekNum);
    List<Map<String, Object>> rsList = new ArrayList<>();
    for (int i = 1; i < 9; i++) {
      calendar.setWeekDate(yyyy, weekNum, 2);
      //获得Calendar的时间
      Date starttime = calendar.getTime();
      //获得指定年的第几周的结束日期
      calendar.setWeekDate(yyyy, weekNum, 1);
      Date endtime = calendar.getTime();
      //将时间戳格式化为指定格式
      String dateStart = simpleDateFormat.format(starttime);
      String dateEnd = simpleDateFormat.format(endtime);
      weekNum--;
      Map<String, Object> map = new HashMap<>();
      map.put("startDate", dateStart + " 00:00:00");
      map.put("endDate", dateEnd + " 59:59:59");
      map.put("fullDate", dateEnd);
      map.put("dateAbscissa", simpleDateFormat3.format(starttime) + "" + simpleDateFormat3.format(endtime));
      rsList.add(map);
    }
    countDb(rsList, projectIds);
    return R.ok().put("rs", rsList);
  }

  private void countDb(List<Map<String, Object>> rsList, String[] projectIds) {
    for (Map<String, Object> map : rsList) {
      int count = receptionService.count(new QueryWrapper<ReceptionEntity>()
        .lambda()
        .gt(ReceptionEntity::getCreateTime, map.get("startDate"))
        .lt(ReceptionEntity::getCreateTime, map.get("endDate"))
        .in(ReceptionEntity::getProjectId, projectIds));
      map.put("num", count);
    }
  }

  private R dayConut(String[] projectIds, Object endDate, int t) {
    if (endDate == null || endDate.equals("")) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String format = sdf.format(new Date());
      endDate = format;
    }
    List<Map> maps;
    if (t == 1) {
      maps = receptionService.groupByDateCount(endDate.toString(), projectIds);
    } else {
      maps = busiCustomerService.groupByDateCountDay(endDate.toString(), projectIds);
    }
    return R.ok().put("rs", maps);
  }

  /**
   * 列表
   */
  @RequestMapping("/listByDate")
  public R listByDate(@RequestBody Map<String, Object> params) throws ParseException {
    Object projectId = params.get("projectId");
    if (null == projectId) {
      return R.error("请选择当前要查看的项目");
    } else {
      Map<String, String> stringStringMap = dateTo(params);
      params.put("startDate", stringStringMap.get("startDate"));
      params.put("endDate", stringStringMap.get("endDate"));
      PageUtils maps = receptionService.listBySalerId(params);
      return R.ok().put("page", maps);
    }
  }

  /**
   * 列表
   */
  @RequestMapping("/renchouCount")
  public R rengouCount(@RequestParam Map<String, Object> params) throws ParseException {

    return tongyongQuery(params, 2);
  }
  /**
   * 分析统计
   *
   * @return
   */
  @RequestMapping("/statisticsComInfo")
  public R statisticsComInfo(@RequestParam Map<String, Object> params) throws ParseException {
    return tongyongQuery(params, 1);
  }

  public R tongyongQuery(Map<String, Object> params,int type) throws ParseException {

    Map<String, Integer> rs;
    Object projectId = params.get("projectId");
    if (null == projectId) {
      return R.error("请选择当前要查看的项目");
    } else {
      String[] ids = projectId.toString().split(",");
      Map<String,String> map = dateTo(params);
      if (type == 1) {
        rs = getComInfo(map.get("startDate"), map.get("endDate"), ids);
        return R.ok().put("rs", rs);
      }else if (type == 2){
        return R.ok().put("rs",getRenChouCount(map.get("startDate"), map.get("endDate"), ids));
      }
      return null;
    }
  }

  private Map<String,String> dateTo(Map<String, Object> params) throws ParseException {
    String dateStart = null;
    String dateEnd = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Object date = params.get("date");
    if (date == null || date.equals("")) {
      String format = sdf.format(new Date());
      date = format;
    }
    Object unit = params.get("unit");
    if (unit.toString().equals("day")) {
      dateStart = date + " 00:00:00";
      dateEnd = date + " 59:59:59";
    } else if (unit.toString().equals("week")) {
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(sdf.parse(date.toString()));
      calendar.add(calendar.DATE, -6);
      dateStart = sdf.format(calendar.getTime()) + " 00:00:00";
      dateEnd = date + " 59:59:59";
    } else if (unit.toString().equals("month")) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
      Date parse = format.parse(date.toString());
      dateStart = format.format(parse) + "-01 00:00:00";
      dateEnd = date + " 59:59:59";
    } else if (unit.toString().equals("year")) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy");
      Date parse = format.parse(date.toString());
      String yearStr = format.format(parse);
      dateStart = yearStr + "-01-01 00:00:00";
      dateEnd = yearStr + "-12-31 59:59:59";
    }
    Map<String,String> rs = new HashMap<>();
    rs.put("startDate",dateStart);
    rs.put("endDate",dateEnd);
    return rs;
  }

  /**
   * 查询认筹
   * @param dateStart
   * @param dateEnd
   * @param projectIds
   * @return
   */
  private List<Map<String,Object>> getRenChouCount(String dateStart, String dateEnd, String[] projectIds){
    Map param = new HashMap();
    param.put("dateStart",dateStart);
    param.put("dateEnd",dateEnd);
    param.put("projectIds",projectIds);
    List<Map<String,Object>> map = busiBookingService.renchouGroup(param);
    return map;
  }

  private Map<String, Integer> getComInfo(String dateStart, String dateEnd, String[] projectIds) {
    int count = receptionService.count(new QueryWrapper<ReceptionEntity>()
      .lambda()
      .gt(ReceptionEntity::getCreateTime, dateStart)
      .lt(ReceptionEntity::getCreateTime, dateEnd)
      .in(ReceptionEntity::getProjectId, projectIds));

    int countNew = receptionService.count(new QueryWrapper<ReceptionEntity>()
      .lambda()
      .eq(ReceptionEntity::getIsNew, 1)
      .gt(ReceptionEntity::getCreateTime, dateStart)
      .lt(ReceptionEntity::getCreateTime, dateEnd)
      .in(ReceptionEntity::getProjectId, projectIds));

    int countOld = receptionService.count(new QueryWrapper<ReceptionEntity>()
      .lambda()
      .eq(ReceptionEntity::getIsNew, 0)
      .gt(ReceptionEntity::getCreateTime, dateStart)
      .lt(ReceptionEntity::getCreateTime, dateEnd)
      .in(ReceptionEntity::getProjectId, projectIds));

    int countYcl = receptionService.count(new QueryWrapper<ReceptionEntity>()
      .lambda().eq(ReceptionEntity::getStatus, 1)
      .gt(ReceptionEntity::getCreateTime, dateStart)
      .lt(ReceptionEntity::getCreateTime, dateEnd)
      .in(ReceptionEntity::getProjectId, projectIds));


    int countWcl = receptionService.count(new QueryWrapper<ReceptionEntity>()
      .lambda().eq(ReceptionEntity::getStatus, 0)
      .gt(ReceptionEntity::getCreateTime, dateStart)
      .lt(ReceptionEntity::getCreateTime, dateEnd)
      .in(ReceptionEntity::getProjectId, projectIds));
    Map<String, Integer> rs = new HashMap<>();
    rs.put("countNew", countNew);
    rs.put("countOld", countOld);
    rs.put("countYcl", countYcl);
    rs.put("countWcl", countWcl);
    rs.put("count", count);
    return rs;
  }


  /**
   * 客户分组统计
   */
  @RequestMapping("/groupList")
  public R groupList(@RequestParam Map<String, Object> params) {
    List<Integer> projectIds = getProjectIds();

    return R.ok()
      .put("timeoutCount", busiCustomerService
        .countTimeout(projectIds))
      .put("normalCount", busiCustomerService.countNormal(projectIds))
      .put("recoveryCount", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>().lambda().in(BusiCustomerEntity::getProjectId, projectIds).isNull(BusiCustomerEntity::getMatchUserId).eq(BusiCustomerEntity::getStatus, 2)))
      .put("repetitionCount", busiCustomerService.countRepetition(projectIds))
      .put("collideCount", busiCustomerService.countCollide(projectIds))
      .put("invalidCount", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>()
        .lambda().in(BusiCustomerEntity::getProjectId, projectIds)
        .eq(BusiCustomerEntity::getInvalid, 1).eq(BusiCustomerEntity::getStatus, 1)))
      .put("successCount", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>().lambda().in(BusiCustomerEntity::getStatus, 1, 2).in(BusiCustomerEntity::getBusiStatus, 50, 60)))
      .put("unSuccessCount", busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>().lambda().in(BusiCustomerEntity::getStatus, 1, 2).notIn(BusiCustomerEntity::getBusiStatus, 50, 60)));

  }

  /**
   * 正常跟进客户分组列表
   */
  @RequestMapping("/groupNormalFollowList")
  public R groupNormalFollowList(@RequestParam Map<String, Object> params) {
    List<Integer> projectIds = getProjectIds();
    return R.ok().put("datas", sysUserService.queryNormalFollow(projectIds));
  }

  /**
   * 逾期客户
   */
  @RequestMapping("/groupTimeoutList")
  public R groupTimeoutList(@RequestParam Map<String, Object> params) {
    List<Integer> projectIds = getProjectIds();
    List<SysUserEntity> list = null;
    Object ot = params.get("type");

    if ("success".equals(ot)) {
      //成交客户
      list = sysUserService.querySuccessList(projectIds);
    } else if ("unSuccess".equals(ot)) {
      //未成交客户
      list = sysUserService.queryUnSuccessList(projectIds);
    } else {
      //逾期客户
      ot = "timeOut";
      list = sysUserService.queryTimeoutList(projectIds);
    }
    for (SysUserEntity user : list) {
      if (user.getUserId() == null) {
        user.setUserId(-1l);
        user.setUsername("公共客户");
        user.setName("公共客户");
      }
    }

    return R.ok().put("datas", list);

  }

  /**
   * 正常跟进客户列表
   */
  @RequestMapping("/normalFollowList")
  public R List(@RequestParam Map<String, Object> params) {
    List<Integer> projectIds = getProjectIds();
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.normalFollowPage(iPage, params.get("userId").toString(), projectIds);
    return R.ok().put("page", new PageUtils(iPage));
  }

  /**
   * 逾期客户列表
   */
  @RequestMapping("/timeoutList")
  public R timeoutList(@RequestParam Map<String, Object> params) {
    if (params.get("userId") == null) {
      return R.error("参数异常");
    }
    List<Integer> projectIds = new ArrayList<>();
    if(params.get("projectId") == null){
      projectIds = getProjectIds();
    }else{
      projectIds.add(Integer.parseInt(params.get("projectId").toString()));
    }

    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    Object ot = params.get("type");
    if ("success".equals(ot)) {
      iPage = busiCustomerService.successPage(iPage, params.get("userId").toString(), projectIds, null);
    } else if ("unSuccess".equals(ot)) {
      iPage = busiCustomerService.unSuccessPage(iPage, params.get("userId").toString(), projectIds, null);
    } else {
      iPage = busiCustomerService.timeoutPage(iPage, params.get("userId").toString(), projectIds, null);
    }
    return R.ok().put("page", new PageUtils(iPage));
  }

  /**
   * 逾期客户列表
   */
  @RequestMapping("/allTimeoutList")
  public R allTimeoutList(@RequestParam Map<String, Object> params) {
    String keywords = ParamResolvor.getString(params, "keyword");
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.timeoutPage(iPage, null, null, keywords);
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
    List<Integer> projectIds = getProjectIds();
    String keywords = ParamResolvor.getString(params, "keyword");
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.publicPage(iPage, projectIds, keywords, ParamResolvor.getInt(params, "stt"), ParamResolvor.getLong(params, "matchUserId"));
    return R.ok().put("page", new PageUtils(iPage));
  }

  /**
   * 无效客户
   */
  @RequestMapping("/invalidList")
  public R invalidList(@RequestParam Map<String, Object> params) {
    if (params.get("projectId") == null) {
      return R.error("参数异常");
    }
    params.put("invalid", 1);
    params.put("status", 1);
    PageUtils pageUtils = busiCustomerService.queryPage(params);
    return R.ok().put("page", pageUtils);
  }

  /**
   * 重复客户分组统计
   */
  @RequestMapping("/groupRepetitionList")
  public R groupRepetitionList(@RequestParam Map<String, Object> params) {
    List<Integer> projectIds = getProjectIds();
    params.put("projectIds", projectIds);
    PageUtils maps = busiCustomerService.groupRepetition(params);
    return R.ok().put("page", maps);
  }

  /**
   * 撞到客户统计
   */
  @RequestMapping("/collideList")
  public R collideList(@RequestParam Map<String, Object> params) {
    List<Integer> projectIds = getProjectIds();
    params.put("projectIds", projectIds);
    PageUtils maps = busiCustomerService.collideList(params);
    return R.ok().put("page", maps);
  }


  /**
   * 垃圾箱
   */
  @RequestMapping("/rubbishList")
  public R rubbishList(@RequestParam Map<String, Object> params) {
    List<Integer> projectIds = getProjectIds();
    IPage<BusiCustomerEntity> iPage = new Query<BusiCustomerEntity>().getPage(params);
    iPage = busiCustomerService.publicPage(iPage, projectIds, null, ParamResolvor.getInt(params, "stt"), ParamResolvor.getLong(params, "oldMatchUserId"));
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
        roam.setRemark("垃圾箱，被" + getUser().getName() + "扔进垃圾箱");
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
      busiCustomerService.recovery(ids, getUser().getName());
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
      BusiCustomerEntity busiCustomerEntity = busiCustomerService.getById(customerIds[0]);
      int k = busiCustomerService.count(new QueryWrapper<BusiCustomerEntity>().lambda().in(BusiCustomerEntity::getId,customerIds).ne(BusiCustomerEntity::getProjectId,busiCustomerEntity.getProjectId()));
      if(k>0){
        return R.error("只能选择同一个项目的客户进行分配");
      }
      BusiCustomerEntity e = busiCustomerService.getById(customerIds[0]);
      int c = busiUserProjectService.count(new QueryWrapper<BusiUserProjectEntity>().lambda().in(BusiUserProjectEntity::getUserId,userIds).eq(BusiUserProjectEntity::getProjectId,e.getProjectId()));
      if(c < userIds.length){
        return R.error("选择的置业顾问没有该客户的权限");
      }

      int i = 0;
      for (String customerId : customerIds) {
        if (i == userIds.length) {
          i = 0;
        }
        String userId = userIds[i];

        BusiCustomerRoamEntity roam = new BusiCustomerRoamEntity();
        roam.setCreateTime(new Date());
        roam.setCustomerId(Integer.parseInt(customerId));

        BusiCustomerEntity entity = busiCustomerService.getById(customerId);
        SysUserEntity sysUserEntity = sysUserService.getById(entity.getMatchUserId());
        roam.setUserId(Integer.parseInt(userId));
        roam.setRemark("分配，被" + getUser().getName() + "分配至" + sysUserService.getById(userId).getName());
        busiCustomerRoamService.save(roam);
        if (sysUserEntity == null) {
          sysUserEntity = new SysUserEntity();
        }
        busiCustomerService.update(new UpdateWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getId, customerId).set(BusiCustomerEntity::getOldMatchUserId, sysUserEntity.getUserId())
          .set(BusiCustomerEntity::getOldMatchUserName, sysUserEntity.getName()).set(BusiCustomerEntity::getStatus, 1).set(BusiCustomerEntity::getMatchUserId, userId).set(BusiCustomerEntity::getMatchUserTime, new Date())
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
