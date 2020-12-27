/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.task;

import io.renren.modules.busi.entity.BusiCustomerEntity;
import io.renren.modules.busi.service.BusiCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author gale
 * @Date 2020/12/25
 * @Version 1.0
 * @Description
 **/
@Component("customerStatusTask")
public class CustomerStatusTask implements ITask {
  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  public BusiCustomerService busiCustomerService;

  @Override
  public void run(String params) {
    logger.debug("CustomerStatusTask定时任务正在执行，参数为：{}", params);
    List<BusiCustomerEntity> customers = busiCustomerService.queryCallVisit();
    String[] ids = new String[customers.size()];
    for (int i = 0; i < customers.size(); i++) {
      ids[i] = customers.get(i).getId().toString();
    }
    busiCustomerService.recovery(ids, "系统");
  }
}
