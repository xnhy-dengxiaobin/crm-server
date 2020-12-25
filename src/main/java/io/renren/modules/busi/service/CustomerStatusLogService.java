package io.renren.modules.busi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.ParamResolvor;
import io.renren.modules.busi.bean.ActionEnum;
import io.renren.modules.busi.bean.BusiStatusEnum;
import io.renren.modules.busi.constant.Constant;
import io.renren.modules.busi.entity.CustomerStatusLogEntity;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuh
 * @email 18067435857@163.com
 * @date 2020-12-22 22:29:25
 */
public interface CustomerStatusLogService extends IService<CustomerStatusLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CustomerStatusLogEntity> queryList(Map<String, Object> params);

    /**
     * 添加日志：报备/重复报备-审核中
     *
     * @param customerId
     * @param prepareId
     * @param isRepeat   是否重复报备
     * @return
     */
    default CustomerStatusLogEntity prepareAudit(int customerId, int prepareId, boolean isRepeat) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.PREPARE.getLabel();
        String status = BusiStatusEnum.PREPARE_AUDITING.getLabel();
        if (isRepeat) {
            action = ActionEnum.RE_PREPARE.getLabel();
        }
        Map<String, Object> actionMap = ParamResolvor.getMap(Constant.customerStatusLog, action);
        Map<String, Object> statusMap = ParamResolvor.getMap(actionMap, status);
        String memo1 = rep(ParamResolvor.getString(statusMap, "memo1"), "$date$", DateUtils.format(new Date()));
        String memo2 = ParamResolvor.getString(statusMap, "memo2");
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setMemo2(memo2);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(prepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：报备/重复报备-拒收无效
     *
     * @param customerId
     * @param prepareId
     * @return
     */
    default CustomerStatusLogEntity prepareReject(int customerId, int prepareId, String reason, boolean isRepeat) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.PREPARE.getLabel();
        String status = BusiStatusEnum.PREPARE_AUDITING.getLabel();
        if (isRepeat) {
            action = ActionEnum.RE_PREPARE.getLabel();
        }
        Map<String, Object> actionMap = ParamResolvor.getMap(Constant.customerStatusLog, action);
        Map<String, Object> statusMap = ParamResolvor.getMap(actionMap, status);
        String memo1 = rep(ParamResolvor.getString(statusMap, "memo1"), "$reason$", reason);
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(prepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：过期-过期无效
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity expiredInvalid(int customerId, int parepareId) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.EXPIRED.getLabel();
        String status = BusiStatusEnum.PREPARE_EXPIRED.getLabel();
        Map<String, Object> actionMap = ParamResolvor.getMap(Constant.customerStatusLog, action);
        Map<String, Object> statusMap = ParamResolvor.getMap(actionMap, status);
        String memo1 = ParamResolvor.getString(statusMap, "memo1");
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：来访-有效
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity visited(int customerId, int parepareId) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.VISITED.getLabel();
        String status = BusiStatusEnum.PREPARE_OK.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = rep(ParamResolvor.getString(statusMap, "memo1"), "$date$", DateUtils.format(new Date()));
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：人工确客-接收
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity mannulReceive(int customerId, int parepareId) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.MANNUL_AUDIT.getLabel();
        String status = BusiStatusEnum.CUS_VISITED.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = rep(ParamResolvor.getString(statusMap, "memo1"), "$date$", DateUtils.format(new Date()));
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：人工确客-手工无效
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity mannulReject(int customerId, int parepareId, String reason) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.MANNUL_AUDIT.getLabel();
        String status = BusiStatusEnum.PREPARE_MANNUL_REJECT.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = rep(ParamResolvor.getString(statusMap, "memo1"), "$reason$", reason);
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：保护期变更-认筹
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity periodChangeSolicit(int customerId, int parepareId) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.PROTECT_PERIOD_CHANGE.getLabel();
        String status = BusiStatusEnum.CUS_SOLICITED.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = ParamResolvor.getString(statusMap, "memo1");
        String memo2 = ParamResolvor.getString(statusMap, "memo2");
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setMemo2(memo2);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：保护期变更-认购
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity periodChangeSubscribe(int customerId, int parepareId) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.PROTECT_PERIOD_CHANGE.getLabel();
        String status = BusiStatusEnum.CUS_SUBSCRIBED.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = ParamResolvor.getString(statusMap, "memo1");
        String memo2 = ParamResolvor.getString(statusMap, "memo2");
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setMemo2(memo2);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：认筹-认筹
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity solicit(int customerId, int parepareId) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.SOLICIT.getLabel();
        String status = BusiStatusEnum.CUS_SOLICITED.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = ParamResolvor.getString(statusMap, "memo1");
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：认购-认购
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity subscribe(int customerId, int parepareId, String room) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.SUBSCRIBE.getLabel();
        String status = BusiStatusEnum.CUS_SUBSCRIBED.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = rep(ParamResolvor.getString(statusMap, "memo1"), "$room$", room);
        String memo2 = rep(ParamResolvor.getString(statusMap, "memo2"), "$date$", DateUtils.format(new Date()));
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setMemo2(memo2);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：交易作废-认购作废
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity subscribeDiscard(int customerId, int parepareId, String reason, String room) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.TRANSCTION_VOID.getLabel();
        String status = BusiStatusEnum.CUS_SUBSCRIBED_DISCARD.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = rep(ParamResolvor.getString(statusMap, "memo1"), "$reason$", reason);
        String memo2 = rep(ParamResolvor.getString(statusMap, "memo2"), "$room$", room);
        String memo3 = rep(ParamResolvor.getString(statusMap, "memo3"), "$date$", DateUtils.format(new Date()));
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setMemo2(memo2);
        customerStatusLogEntity.setMemo3(memo3);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    /**
     * 添加日志：签约-签约
     *
     * @param customerId
     * @param parepareId
     * @return
     */
    default CustomerStatusLogEntity sign(int customerId, int parepareId, String room) {
        CustomerStatusLogEntity customerStatusLogEntity = new CustomerStatusLogEntity();
        String action = ActionEnum.SIGN.getLabel();
        String status = BusiStatusEnum.CUS_SIGNED.getLabel();
        Map<String, Object> statusMap = getStatusMap(action, status);
        String memo1 = rep(ParamResolvor.getString(statusMap, "memo1"), "$room$", room);
        String memo2 = rep(ParamResolvor.getString(statusMap, "memo2"), "$date$", DateUtils.format(new Date()));
        customerStatusLogEntity.setAction(action);
        customerStatusLogEntity.setStatus(status);
        customerStatusLogEntity.setMemo1(memo1);
        customerStatusLogEntity.setMemo2(memo2);
        customerStatusLogEntity.setFoundTime(new Date());
        customerStatusLogEntity.setCustomerId(customerId);
        customerStatusLogEntity.setPrepareId(parepareId);
        return customerStatusLogEntity;
    }

    default Map<String, Object> getStatusMap(String action, String status) {
        Map<String, Object> actionMap = ParamResolvor.getMap(Constant.customerStatusLog, action);
        Map<String, Object> statusMap = ParamResolvor.getMap(actionMap, status);
        return statusMap;
    }

    default String rep(String str, String key, String value) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return str.replaceAll(key, value);
    }
}

