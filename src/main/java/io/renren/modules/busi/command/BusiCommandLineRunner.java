package io.renren.modules.busi.command;

import io.renren.common.utils.JsonUtil;
import io.renren.modules.busi.constant.Constant;
import io.renren.modules.sys.dao.SysConfigDao;
import io.renren.modules.sys.service.SetupService;
import io.renren.modules.sys.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class BusiCommandLineRunner implements CommandLineRunner {

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${crm.sync.host}")
    private String syncHost;

    @Override
    public void run(String... args) throws Exception {
        log.debug("初始化系统参数");
        String channelGranteePeriod = sysConfigService.getValue(Constant.CHANNEL_GRANTEE_PERIOD);
        String prepareValidPeriod = sysConfigService.getValue(Constant.PREPARE_VALID_PERIOD);
        String salerGranteePeriod = sysConfigService.getValue(Constant.SALER_GRANTEE_PERIOD);
        String str = sysConfigService.getValue(Constant.CUSTOMER_STAUTS_LOG);

        Constant.channelGranteePeriod = Integer.valueOf(StringUtils.isEmpty(channelGranteePeriod) ? "30" : channelGranteePeriod);
        Constant.prepareValidPeriod = Integer.valueOf(StringUtils.isEmpty(prepareValidPeriod) ? "2" : prepareValidPeriod);
        Constant.salerGranteePeriod = Integer.valueOf(StringUtils.isEmpty(salerGranteePeriod) ? "30" : salerGranteePeriod);
        Constant.customerStatusLog = JsonUtil.readJsonObject(str, Map.class);

        Constant.SYNC_HOST = syncHost;

        log.debug("crm服务启动成功");
    }
}
