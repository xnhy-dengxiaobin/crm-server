package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiProjectEntity;
import io.renren.modules.busi.entity.BusiUserProjectEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author æå¤§é¾
 * @email 870455116@qq.com
 * @date 2020-11-22 23:09:02
 */
public interface BusiUserProjectService extends IService<BusiUserProjectEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据当前用户的查询关联项目
     * @param userId
     * @return List<BusiProjectEntity>
     */
    List<BusiProjectEntity> queryProjectByUser(Long userId);

    /**
     * 保存用户和项目的关联
     * @param userId
     * @param projectIds
     */
    void increaseOrModify(Long userId, List<Long> projectIds);
}

