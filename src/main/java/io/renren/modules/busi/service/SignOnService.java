package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.SignOnEntity;

import java.util.Map;

/**
 * 员工签到表

 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-04 20:26:45
 */
public interface SignOnService extends IService<SignOnEntity> {

    PageUtils queryPage(Map<String, Object> params);

    boolean isSignOn(Integer userId);
}

