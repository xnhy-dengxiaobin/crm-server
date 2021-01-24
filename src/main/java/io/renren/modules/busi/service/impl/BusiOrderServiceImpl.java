package io.renren.modules.busi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.Query;
import io.renren.modules.busi.dao.BusiOrderDao;
import io.renren.modules.busi.entity.BusiOrderEntity;
import io.renren.modules.busi.service.BusiOrderService;
import io.renren.modules.busi.vo.BusiOrderVO;
import io.renren.modules.busi.vo.BusiTradeVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busiOrderService")
public class BusiOrderServiceImpl extends ServiceImpl<BusiOrderDao, BusiOrderEntity> implements BusiOrderService {

  @Override
  public PageUtils queryPage(Map<String, Object> params) {
    IPage<BusiOrderEntity> page = this.page(
      new Query<BusiOrderEntity>().getPage(params),
      new QueryWrapper<BusiOrderEntity>()
    );

    return new PageUtils(page);
  }

  @Override
  public PageUtils listPage(Map<String, Object> params) {
    long currentPage = ParamResolvor.getLongAsDefault(params, "page", 1);
    long limit = ParamResolvor.getLongAsDefault(params, "limit", 10);
    long offset = (currentPage - 1) * limit;
    params.put("offset", offset);
    params.put("limit", limit); //将string转为long
    List<BusiOrderEntity> list = getBaseMapper().listPage(params);
    Integer cnt = getBaseMapper().listPageCount(params);
    Page<BusiOrderEntity> page = new Page<>();
    page.setCurrent(currentPage);
    page.setSize(limit);
    page.setTotal(cnt);
    page.setRecords(list);
    return new PageUtils(page);
  }
  @Override
  public IPage<BusiTradeVO> promptPage(List<Integer> ids,IPage<BusiTradeVO> iPage, String condition) {
    return baseMapper.promptPage(ids,iPage,condition);
  }

}
