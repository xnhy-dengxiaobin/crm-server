package io.renren.modules.busi.job;

import io.renren.modules.job.task.ITask;

/**
 * 同步房源信息到crm中
 * 如果房源状态有变化，生成状态变更记录
 */
public class RoomSyncJob implements ITask {
    @Override
    public void run(String params) {

    }
}
