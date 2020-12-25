package io.renren.modules.busi.constant;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * 常量
 */
public class Constant {
    public static final String CHANNEL_GRANTEE_PERIOD = "CHANNEL_GRANTEE_PERIOD"; //渠道确客之后对经纪人的保护期
    public static final String PREPARE_VALID_PERIOD = "PREPARE_VALID_PERIOD"; //渠道报备有效期。单位：天
    public static final String SALER_GRANTEE_PERIOD = "SALER_GRANTEE_PERIOD"; //分配置业顾问后的保护期。单位：天
    public static final String CUSTOMER_STAUTS_LOG = "CUSTOMER_STAUTS_LOG"; //客户状态变化日志

    public static int channelGranteePeriod = 2;
    public static int prepareValidPeriod = 30;
    public static int salerGranteePeriod = 30;
    public static Map<String, Object> customerStatusLog = new HashedMap();

    public static final String MANNUL_REJECT_REASON = "其它渠道带客户到访，以带看为准。";
}
