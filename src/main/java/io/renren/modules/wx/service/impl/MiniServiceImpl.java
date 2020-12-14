package io.renren.modules.wx.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.api.impl.WxMaUserServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.renren.common.utils.HttpUtils;
import io.renren.common.utils.JsonUtil;
import io.renren.common.utils.ParamResolvor;
import io.renren.modules.wx.config.WxProp;
import io.renren.modules.wx.service.MiniService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class MiniServiceImpl implements MiniService {
    @Autowired
    private WxProp wxProp;


    @Autowired
    private WxMaService wxMaService;

    @Override
    public WxMaJscode2SessionResult getSessionKey(String code) {
        try {
            WxMaJscode2SessionResult codeObj = wxMaService.jsCode2SessionInfo(code);
            return codeObj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File createQrCode(String code, int width) {
        try {
            return wxMaService.getQrcodeService().createQrcode(code, width);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendTemplateMessage(String touser, String template_id, String page, String form_id, Map<String, Object> data, String emphasis_keyword) {
        try {
            String accessToken = wxMaService.getAccessToken();
            String url = wxProp.getMini().getBaseUrl() + wxProp.getMini().getSendTemplateMessageApi() + accessToken;

            Map<String, Object> param = new HashMap<String, Object>() {{
                put("touser", touser);
                put("template_id", template_id);
                put("data", data);
            }};
            if (StringUtils.isNotEmpty(page)) {
                param.put("page", page);
            }
            if (StringUtils.isNotEmpty(form_id)) {
                param.put("form_id", form_id);
            }
            if (StringUtils.isNotEmpty(emphasis_keyword)) {
                param.put("emphasis_keyword", emphasis_keyword);
            }

            HttpResponse res = HttpUtils.doPost(url, JsonUtil.objectToJson(param));
            Map<String, Object> dataMap = HttpUtils.res2Map(res);
            if (MapUtils.isEmpty(dataMap)) {
                throw new Exception("发送消息出错");
            }

            if (ParamResolvor.getInt(dataMap, "errcode") != 0) {
                throw new Exception(ParamResolvor.getString(dataMap, "errmsg"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendUniformMessage2Cp(String touser, String template_id, Map<String, Object> data, String page, String jump) {
        try {
            String accessToken = wxMaService.getAccessToken();
            String url = wxProp.getMini().getBaseUrl() + wxProp.getMini().getSendUniformMessageApi() + accessToken;

            Map<String, Object> miniprogram = new HashMap<String, Object>() {{
                put("appid", wxProp.getMini().getAppId());
            }};

            if (StringUtils.isNotEmpty(page)) {
                miniprogram.put("page", page);
            }

            Map<String, Object> mp_template_msg = new HashMap<String, Object>() {{
                put("appid", wxProp.getMp().getAppId());
                put("template_id", template_id);
                put("miniprogram", miniprogram);
                put("data", data);
            }};

            if (StringUtils.isNotEmpty(jump)) {
                mp_template_msg.put("url", jump);
            }

            Map<String, Object> template = new HashMap<String, Object>() {{
                put("touser", touser);
                put("mp_template_msg", mp_template_msg);
            }};

            String json = JsonUtil.objectToJson(template);
            HttpResponse res = HttpUtils.doPost(url, json);
            Map<String, Object> dataMap = HttpUtils.res2Map(res);
            if (MapUtils.isEmpty(data)) {
                throw new Exception("发送消息出错");
            }

            if (ParamResolvor.getInt(dataMap, "errcode") != 0) {
                throw new Exception(ParamResolvor.getString(dataMap, "errmsg"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMaPhoneNumberInfo getPhoneNumber(String code, String encryptedData, String iv) {
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            WxMaPhoneNumberInfo phoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(session.getSessionKey(), encryptedData, iv);
            return phoneNumberInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMaPhoneNumberInfo getPhoneNumberBySessionKey(String sessionKey, String encryptedData, String iv) {
        try {
            WxMaPhoneNumberInfo phoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
            return phoneNumberInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMaUserInfo getUserInfo(String code, String encryptedData, String iv) {
        try {
            WxMaJscode2SessionResult codeObj = wxMaService.jsCode2SessionInfo(code);
            WxMaUserService wxUserService = new WxMaUserServiceImpl(wxMaService);
            WxMaUserInfo wxMaUserInfo = wxUserService.getUserInfo(codeObj.getSessionKey(), encryptedData, iv);
            return wxMaUserInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMaUserInfo getUserInfoBySessionKey(String sessionKey, String encryptedData, String iv) {
        try {
            WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
            return wxMaUserInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
