package io.renren.modules.wx.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.val;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信相关配置
 */
@Configuration
public class WxConfig {
    @Autowired
    private WxProp wxProp;

    @Bean
    public WxMaConfig wxMaConfig() {
        val config = new WxMaDefaultConfigImpl();
        config.setAppid(wxProp.getMini().getAppId());
        config.setSecret(wxProp.getMini().getAppSecret());
        return config;
    }

    @Bean
    public WxMaService wxMaService() {
        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig());
        return wxMaService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfig() {
        WxMpDefaultConfigImpl wxMpConfigStorage = new WxMpDefaultConfigImpl();
        wxMpConfigStorage.setAppId(wxProp.getMp().getAppId());
        wxMpConfigStorage.setSecret(wxProp.getMp().getAppSecret());
        return wxMpConfigStorage;
    }

    @Bean
    public WxMpService wxMpService() {
        val wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfig());
        return wxMpService;
    }

    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(wxProp.getPay().getAppId());
        wxPayConfig.setMchId(wxProp.getPay().getMchId());
        wxPayConfig.setMchKey(wxProp.getPay().getMchKey());
        wxPayConfig.setSubAppId(wxProp.getPay().getSubAppId());
        wxPayConfig.setSubMchId(wxProp.getPay().getSubMchKey());
        wxPayConfig.setKeyPath(wxProp.getPay().getKeyPath());
        return wxPayConfig;
    }

    @Bean
    public WxPayService wxPayService() {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig());
        return wxPayService;
    }
}
