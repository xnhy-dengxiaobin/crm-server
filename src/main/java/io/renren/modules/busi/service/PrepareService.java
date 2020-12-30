package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.PrepareCheckEntity;
import io.renren.modules.busi.entity.PrepareEntity;

import java.util.List;
import java.util.Map;

/**
 * WX报备表
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-20 11:18:56
 */
public interface PrepareService extends IService<PrepareEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils lstPage(Map<String, Object> params);

    boolean check(PrepareCheckEntity checks);

    String wxSave(PrepareEntity prepare,long userId);

    /**
     * 查询某个报备信息，包含项目、渠道经纪人名称、渠道经纪人电话
     * @param id
     * @return
     */
    PrepareEntity gtById(Integer id);

    PageUtils listPage4Admin(Map<String, Object> params);

    void refreshExpired(List<Integer> ids);
}

