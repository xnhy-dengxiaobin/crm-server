package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.entity.ReceptionEntity;

import java.util.List;
import java.util.Map;

/**
 * 接待记录
 *
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-11-29 00:51:30
 */
public interface ReceptionService extends IService<ReceptionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存接待记录
     * 如果客户中的置业顾问变化了，还要修改客户记录
     * @param receptionEntity
     * @param busiCustomerEntity
     */
    void saveReception(ReceptionEntity receptionEntity, BusiCustomerEntity busiCustomerEntity);

    PageUtils qryPage(Map<String, Object> params);

    PageUtils listBySalerId(Map<String, Object> params);

    List<Map> groupByDateCount(String endDate,Integer projectId);

}

