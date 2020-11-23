package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.SysUserTokenEntity;

import java.util.Map;

/**
 * 系统用户Token
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

