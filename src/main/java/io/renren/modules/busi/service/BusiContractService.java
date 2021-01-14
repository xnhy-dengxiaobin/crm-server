package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiContractEntity;

import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2021-01-14 14:29:24
 */
public interface BusiContractService extends IService<BusiContractEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

