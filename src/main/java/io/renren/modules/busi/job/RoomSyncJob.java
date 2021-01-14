package io.renren.modules.busi.job;

import io.renren.modules.busi.bean.EnnParam;
import io.renren.modules.busi.utils.DataUtil;
import io.renren.modules.job.task.ITask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 同步房源信息到crm中
 * 如果房源状态有变化，生成状态变更记录
 */
@Component
@Slf4j
public class RoomSyncJob implements ITask {
    @Override
    public void run(String params) throws Exception {
        List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/RoomSyncStep"));
        EnnParam ennParam = EnnParam.mkExecParam(steps);
        Map<String, String> param = new HashMap<>();
        DataUtil dataUtil = new DataUtil("sync", "sync", "crm");
        dataUtil.login("admin", "111111");
        try {
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        RoomSyncJob job = new RoomSyncJob();
        job.run("");
    }
}
