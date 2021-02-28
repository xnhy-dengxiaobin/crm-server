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
@Slf4j
@Component
public class StatisticSyncJob implements ITask {
    @Override
    public void run(String params) throws Exception {
        DataUtil dataUtil = new DataUtil("sync", "sync", "crm");
        dataUtil.login("admin", "111111");

        //认筹
        try {
            List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/BookingSyncStep"));
            EnnParam ennParam = EnnParam.mkExecParam(steps);
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
        log.debug("已发送认筹同步请求");

        //认购
        try {
            List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/TradeSyncStep"));
            EnnParam ennParam = EnnParam.mkExecParam(steps);
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
        log.debug("已发送认购同步请求");

        //签约
        try {
            List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/ContractSyncStep"));
            EnnParam ennParam = EnnParam.mkExecParam(steps);
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
        log.debug("已发送签约同步请求");

        //未认购
        try {
            List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/WrgSyncStep"));
            EnnParam ennParam = EnnParam.mkExecParam(steps);
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
        log.debug("已发送未认购同步请求");

        //未签约
        try {
            List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/WqySyncStep"));
            EnnParam ennParam = EnnParam.mkExecParam(steps);
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
        log.debug("已发送未签约同步请求");

        //未交款
        try {
            List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/WjkSyncStep"));
            EnnParam ennParam = EnnParam.mkExecParam(steps);
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
        log.debug("已发送未交款同步请求");

        //退换房
        try {
            List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/ThfSyncStep"));
            EnnParam ennParam = EnnParam.mkExecParam(steps);
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
        log.debug("已发送退换房同步请求");

        //状态变化日志
        try {
            List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/UpdateCustomerStatusStep"));
            EnnParam ennParam = EnnParam.mkExecParam(steps);
            Map<String, Object> result = dataUtil.exec(ennParam);
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                log.error("连接超时，可以忽略");
            } else {
                log.error("", e);
            }
        }
        log.debug("已发送生成状态变更日志请求");
    }

    public static void main(String[] args) throws Exception {
        DataUtil dataUtil = new DataUtil("sync", "sync", "crm");
        dataUtil.login("admin", "111111");
        List<EnnParam.Step> steps = EnnParam.mkSteps(new EnnParam.Step("grv", "cron/TradeSyncStep"));
        EnnParam ennParam = EnnParam.mkExecParam(steps);
        Map<String, Object> result = dataUtil.exec(ennParam);
    }
}
