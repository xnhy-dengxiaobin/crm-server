package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.MiddleTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 中介来源，类型
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-14 22:01:22
 */
public interface MiddleTypeService extends IService<MiddleTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询所有符合条件的记录
     * @param params
     * @return
     */
    List<MiddleTypeEntity> qryLst(Map<String, Object> params);
}

