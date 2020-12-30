package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 项目
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:03
 */
public interface BusiProjectService extends IService<BusiProjectEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<BusiProjectEntity> queryGroupList(Long userId);
}

