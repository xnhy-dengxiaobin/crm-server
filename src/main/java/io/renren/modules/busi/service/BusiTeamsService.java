package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiTeamsEntity;

import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-12-25 10:23:57
 */
public interface BusiTeamsService extends IService<BusiTeamsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

