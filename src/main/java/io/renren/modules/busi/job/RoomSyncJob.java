package io.renren.modules.busi.job;

import io.renren.common.utils.HttpUtils;
import io.renren.common.utils.JsonUtil;
import io.renren.modules.busi.bean.EnnParam;
import io.renren.modules.busi.utils.DataUtil;
import io.renren.modules.job.task.ITask;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步房源信息到crm中
 * 如果房源状态有变化，生成状态变更记录
 */
public class RoomSyncJob implements ITask {
    @Override
    public void run(String params) throws Exception {
        List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "crm/RoomSyncStep"));
        EnnParam ennParam = EnnParam.mkExecParam(steps);
        Map<String, String> param = new HashMap<>();
        //HttpResponse httpResponse = HttpUtils.doPost("https://scmczd.net/sync-server/core/execBusiness", param);
        //HttpResponse httpResponse = HttpUtils.doPost("http://localhost:6041/sync-server/core/execBusiness", param);
        DataUtil dataUtil = new DataUtil("sync", "sync", "crm");
        dataUtil.login("admin", "111111");
        Map<String, Object> result = dataUtil.exec(ennParam);
        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        RoomSyncJob job = new RoomSyncJob();
        job.run("");
    }
}
