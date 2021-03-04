package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiOrderEntity;
import io.renren.modules.busi.vo.BusiTradeVO;

import java.util.List;
import java.util.Map;

/**
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-29 00:08:46
 */
public interface BusiOrderService extends IService<BusiOrderEntity> {

  PageUtils queryPage(Map<String, Object> params);


  PageUtils listPage(Map<String, Object> params);

  IPage<BusiTradeVO> promptPage(List<Integer> ids,IPage<BusiTradeVO> iPage, String condition);

  IPage<BusiTradeVO> promptPageApp(IPage<BusiTradeVO> iPage, String ywy);
}

