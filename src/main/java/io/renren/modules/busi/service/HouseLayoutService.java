package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.HouseLayoutEntity;

import java.util.Map;

/**
 * 项目户型图
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-28 10:32:11
 */
public interface HouseLayoutService extends IService<HouseLayoutEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据id获取户型图, 包含项目名称
     * @param id
     * @return
     */
    HouseLayoutEntity getById4Project(Integer id);
}

