package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiCustomerDao;
import io.renren.modules.busi.dao.BusiCustomerRoamDao;
import io.renren.modules.busi.dao.ReceptionDao;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.BusiCustomerRoamEntity;
import io.renren.modules.busi.entity.ReceptionEntity;
import io.renren.modules.busi.service.BusiCustomerService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("busiCustomerService")
public class BusiCustomerServiceImpl extends ServiceImpl<BusiCustomerDao, BusiCustomerEntity> implements BusiCustomerService {

  @Autowired
  private ReceptionDao receptionDao;
  @Autowired
  private BusiCustomerRoamDao busiCustomerRoamDao;
  @Autowired
  private SysUserDao sysUserDao;

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    QueryWrapper<BusiCustomerEntity> busiCustomerEntityQueryWrapper = new QueryWrapper<>();
    if (params.get("matchUserId") != null) {
      busiCustomerEntityQueryWrapper.eq("match_user_id", params.get("matchUserId"));
    }
    if (params.get("followUserId") != null) {
      busiCustomerEntityQueryWrapper.and(qw -> qw.ne("follow_user_id", params.get("followUserId")).or().isNull("follow_user_id"));
    }
    if (params.get("condition") != null && !"".equals(params.get("condition"))) {
      busiCustomerEntityQueryWrapper.and(qw -> qw.like("name", params.get("condition")).or().like("mobile_phone", params.get("condition")));
    }
    if (params.get("desc") != null) {
      busiCustomerEntityQueryWrapper.orderByDesc(params.get("desc").toString());
    }
    if (params.get("invalid") != null) {
      busiCustomerEntityQueryWrapper.lambda().eq(BusiCustomerEntity::getInvalid, params.get("invalid"));
    }
    if (params.get("projectId") != null) {
      busiCustomerEntityQueryWrapper.lambda().eq(BusiCustomerEntity::getProjectId, params.get("projectId"));
    }
    if (params.get("status") != null) {
      busiCustomerEntityQueryWrapper.lambda().eq(BusiCustomerEntity::getStatus, params.get("status"));
    }
    if (StringUtils.isNotEmpty(ParamResolvor.getString(params, "name"))) {
      busiCustomerEntityQueryWrapper.and(qw -> qw.like("name", ParamResolvor.getString(params, "name")));
    }
    IPage<BusiCustomerEntity> page = this.page(
      new Query<BusiCustomerEntity>().getPage(params),
      busiCustomerEntityQueryWrapper
    );

    return new PageUtils(page);
  }

  @Override
  public IPage<BusiCustomerEntity> normalFollowPage(IPage<BusiCustomerEntity> page, String userId, List<Integer> projectIds) {
    return baseMapper.normalFollowPage(page, userId, projectIds);
  }

  @Override
  public IPage<BusiCustomerEntity> timeoutPage(IPage<BusiCustomerEntity> page, String userId, List<Integer> projectIds, String keywords) {
    return baseMapper.timeoutPage(page, userId, projectIds, keywords);
  }

  @Override
  public IPage<BusiCustomerEntity> successPage(IPage<BusiCustomerEntity> page, String userId, List<Integer> projectIds, String keywords) {
    return baseMapper.successPage(page,userId,projectIds,keywords);
  }

  @Override
  public IPage<BusiCustomerEntity> unSuccessPage(IPage<BusiCustomerEntity> page, String userId, List<Integer> projectIds, String keywords) {
      return baseMapper.unSuccessPage(page,userId,projectIds,keywords);
  }

  @Override
  public IPage<BusiCustomerEntity> publicPage(IPage<BusiCustomerEntity> page, List<Integer> projectIds, String keywords, Integer stt, Long matchUserId) {
    return baseMapper.publicPage(page, projectIds, keywords, stt, matchUserId);
  }

  @Override
  public long countNormal(List<Integer> projectIds) {
    return baseMapper.countNormal(projectIds);
  }

  @Override
  public long countTimeout(List<Integer> projectIds) {
    return baseMapper.countTimeout(projectIds);
  }

  @Override
  public long countRepetition(List<Integer> projectIds) {
    return baseMapper.countRepetition(projectIds);
  }

  @Override
  public long countCollide(List<Integer> projectIds) {
    return baseMapper.countCollide(projectIds);
  }


  @Override
  public List<BusiCustomerEntity> queryByPhone(Map<String, Object> params) {
    return getBaseMapper().selectByPhone(params);
  }

  @Override
  public List<BusiCustomerEntity> queryCallVisit() {
    return getBaseMapper().queryCallVisit();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void perfect(BusiCustomerEntity busiCustomerEntity) {
    getBaseMapper().updateById(busiCustomerEntity);
    ReceptionEntity receptionEntity = new ReceptionEntity();
    receptionEntity.setStatus(1);//已处理
    receptionDao.update(receptionEntity,
      new UpdateWrapper<ReceptionEntity>()
        .eq("customer_id", busiCustomerEntity.getId()));
  }

  @Override
  public List<Map> groupByDateCountWeek(String endDate, String[] projectIds) {
    List<Map> maps = baseMapper.groupByDateCountWeek(endDate, projectIds);
    return maps;
  }

  @Override
  public List<Map> groupByDateCountDay(String endDate, String[] projectIds) {
    List<Map> maps = baseMapper.groupByDateCountDay(endDate, projectIds);
    return maps;
  }

  @Override
  public List<Map> groupByDateCountMonth(String endDate, String[] projectIds) {
    List<Map> maps = baseMapper.groupByDateCountMonth(endDate, projectIds);
    return maps;
  }

  @Override
  public List<Map> groupByDateCountYear(String endDate, String[] projectIds) {
    List<Map> maps = baseMapper.groupByDateCountYear(endDate, projectIds);
    return maps;
  }

  @Override
  public PageUtils groupRepetition(Map<String, Object> params) {
    long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
    long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
    long offset = (currentPage - 1) * limit;
    params.put("offset", offset);
    params.put("limit", limit); //将string转为long
    List<Map> list = getBaseMapper().groupRepetition(params);
    Long cnt = getBaseMapper().countRepetition((List<Integer>) params.get("projectIds"));
    Page<Map> page = new Page<>();
    page.setCurrent(currentPage);
    page.setSize(limit);
    page.setTotal(cnt);
    page.setRecords(list);
    return new PageUtils(page);
  }

  @Override
  public PageUtils collideList(Map<String, Object> params) {
    long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
    long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
    long offset = (currentPage - 1) * limit;
    params.put("offset", offset);
    params.put("limit", limit); //将string转为long
    List<Map> list = getBaseMapper().collideList(params);
    Long cnt = getBaseMapper().countRepetition((List<Integer>) params.get("projectIds"));
    Page<Map> page = new Page<>();
    page.setCurrent(currentPage);
    page.setSize(limit);
    page.setTotal(cnt);
    page.setRecords(list);
    return new PageUtils(page);
  }

  @Override
  public void recovery(String[] ids, String userName) {
    for (String id : ids) {
      if (org.springframework.util.StringUtils.isEmpty(id)) {
        continue;
      }
      BusiCustomerRoamEntity roam = new BusiCustomerRoamEntity();
      roam.setCreateTime(new Date());
      roam.setCustomerId(Integer.parseInt(id));
      roam.setRemark("回收，被" + userName + "回收");
      busiCustomerRoamDao.insert(roam);
      BusiCustomerEntity entity = getById(id);
      if (entity != null && !org.springframework.util.StringUtils.isEmpty(entity.getMatchUserId())) {
        SysUserEntity sysUserEntity = sysUserDao.selectById(entity.getMatchUserId());
        if (sysUserEntity != null) {
          update(new UpdateWrapper<BusiCustomerEntity>().lambda().eq(BusiCustomerEntity::getId, id).set(BusiCustomerEntity::getOldMatchUserId, sysUserEntity.getUserId())
            .set(BusiCustomerEntity::getOldMatchUserName, sysUserEntity.getName()).set(BusiCustomerEntity::getStatus, 2).set(BusiCustomerEntity::getMatchUserId, null)
          );
        }
      }
    }
  }

}
