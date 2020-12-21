package io.renren.modules.wx.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.error.WxErrorException;

import java.io.File;
import java.util.Map;

/**
 * 小程序操作接口
 */
public interface MiniService {
    /**
     * 获取当前登录用户的sessionKey
     * sessionKey需要缓存
     * 普通获取用户信息、位置信息
     *
     * @param code
     * @return
     */
    WxMaJscode2SessionResult getSessionKey(String code);

    /**
     * 新建小程序二维码
     *
     * @param code
     * @param width
     * @return
     */
    File createQrCode(String code, int width);

    /**
     * 发送模板消息
     */
    void sendTemplateMessage(String touser, String template_id, String page, String form_id,
                             Map<String, Object> data, String emphasis_keyword);

    void sendUniformMessage2Cp(String touser, String template_id, Map<String, Object> data, String page, String jump);

    /**
     * 获取电话号码
     * @param code
     * @param encryptedData
     * @param iv
     * @return
     */
    WxMaPhoneNumberInfo getPhoneNumber(String code, String encryptedData, String iv);

    /**
     * 根据sessionKey获取电话号码
     * @param sessionKey
     * @param encryptedData
     * @param iv
     * @return
     */
    WxMaPhoneNumberInfo getPhoneNumberBySessionKey(String sessionKey, String encryptedData, String iv);

    /**
     * 获取用户信息
     * @param code
     * @param encryptedData
     * @param iv
     * @return
     */
    WxMaUserInfo getUserInfo(String code, String encryptedData, String iv);

    /**
     * 根据sessionkey获取用户信息
     * @param sessionKey
     * @param encryptedData
     * @param iv
     * @return
     */
    WxMaUserInfo getUserInfoBySessionKey(String sessionKey, String encryptedData, String iv);


    /**
     * 根据获取用户信息二维码ID
     * @param userid
     */
    String getUserQrcode(Map<String, Object>  param);



}
