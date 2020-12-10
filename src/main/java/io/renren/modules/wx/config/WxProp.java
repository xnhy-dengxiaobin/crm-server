package io.renren.modules.wx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "wx")
@Component
public class WxProp {
    private Mini mini;
    private Mp mp;
    private Pay pay;

    public Mini getMini() {
        return mini;
    }

    public void setMini(Mini mini) {
        this.mini = mini;
    }

    public Mp getMp() {
        return mp;
    }

    public void setMp(Mp mp) {
        this.mp = mp;
    }

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    public static class Mini {
        private String appId;
        private String appSecret;
        private String accessTokenKey;
        private String accessTokenTimeout;
        private String baseUrl;
        private String accessTokenApi;
        private String sendTemplateMessageApi;
        private String sendUniformMessageApi;
        private String statusMessageId;
        private String alarmMessageId;
        private String sessionKeyUrl;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getAccessTokenKey() {
            return accessTokenKey;
        }

        public void setAccessTokenKey(String accessTokenKey) {
            this.accessTokenKey = accessTokenKey;
        }

        public String getAccessTokenTimeout() {
            return accessTokenTimeout;
        }

        public void setAccessTokenTimeout(String accessTokenTimeout) {
            this.accessTokenTimeout = accessTokenTimeout;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getAccessTokenApi() {
            return accessTokenApi;
        }

        public void setAccessTokenApi(String accessTokenApi) {
            this.accessTokenApi = accessTokenApi;
        }

        public String getSendTemplateMessageApi() {
            return sendTemplateMessageApi;
        }

        public void setSendTemplateMessageApi(String sendTemplateMessageApi) {
            this.sendTemplateMessageApi = sendTemplateMessageApi;
        }

        public String getSendUniformMessageApi() {
            return sendUniformMessageApi;
        }

        public void setSendUniformMessageApi(String sendUniformMessageApi) {
            this.sendUniformMessageApi = sendUniformMessageApi;
        }

        public String getStatusMessageId() {
            return statusMessageId;
        }

        public void setStatusMessageId(String statusMessageId) {
            this.statusMessageId = statusMessageId;
        }

        public String getAlarmMessageId() {
            return alarmMessageId;
        }

        public void setAlarmMessageId(String alarmMessageId) {
            this.alarmMessageId = alarmMessageId;
        }

        public String getSessionKeyUrl() {
            return sessionKeyUrl;
        }

        public void setSessionKeyUrl(String sessionKeyUrl) {
            this.sessionKeyUrl = sessionKeyUrl;
        }
    }

    public static class Mp {
        private String appId;
        private String appSecret;
        private String accessTokenKey;
        private String returnUrl;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getAccessTokenKey() {
            return accessTokenKey;
        }

        public void setAccessTokenKey(String accessTokenKey) {
            this.accessTokenKey = accessTokenKey;
        }

        public String getReturnUrl() {
            return returnUrl;
        }

        public void setReturnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
        }
    }

    public static class Pay {
        private String appId;
        private String mchId;
        private String mchKey;
        private String subAppId;
        private String subMchKey;
        private String keyPath;
        private String notifyUrl;
        private String notifyRefundUrl;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getMchKey() {
            return mchKey;
        }

        public void setMchKey(String mchKey) {
            this.mchKey = mchKey;
        }

        public String getSubAppId() {
            return subAppId;
        }

        public void setSubAppId(String subAppId) {
            this.subAppId = subAppId;
        }

        public String getSubMchKey() {
            return subMchKey;
        }

        public void setSubMchKey(String subMchKey) {
            this.subMchKey = subMchKey;
        }

        public String getKeyPath() {
            return keyPath;
        }

        public void setKeyPath(String keyPath) {
            this.keyPath = keyPath;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        public String getNotifyRefundUrl() {
            return notifyRefundUrl;
        }

        public void setNotifyRefundUrl(String notifyRefundUrl) {
            this.notifyRefundUrl = notifyRefundUrl;
        }
    }
}
