package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.WxuserQrEntity;

import java.util.Map;

/**
 * 个人推广码
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-21 23:17:58
 */
public interface WxuserQrService extends IService<WxuserQrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

