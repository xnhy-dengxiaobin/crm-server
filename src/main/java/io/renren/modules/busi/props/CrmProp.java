package io.renren.modules.busi.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "crm")
public class CrmProp {
    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    private String uploadPath;

    private String logPath;

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public boolean isAdminDebug() {
        return adminDebug;
    }

    public void setAdminDebug(boolean adminDebug) {
        this.adminDebug = adminDebug;
    }

    private boolean adminDebug;

}
