package io.renren.modules.wx.controller;

import io.renren.common.utils.ParamResolvor;
import io.renren.common.utils.R;
import io.renren.modules.wx.config.WxProp;
import io.renren.modules.wx.service.MiniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/wx/mini")
public class WxMiniController {
    @Autowired
    private MiniService miniService;

    @Autowired
    private WxProp wxProp;

    @RequestMapping("/getSessionKey")
    public R getSessionKey(@RequestParam String code) {
        return R.ok().put("sessionKey", miniService.getSessionKey(code));
    }

    @RequestMapping("/getUserInfo")
    public R getUserInfo(@RequestBody Map<String, Object> param) {
//        return R.ok().put("userInfo", miniService.getUserInfo(ParamResolvor.getString(param, "code"),ParamResolvor.getString(param, "encryptedData"),ParamResolvor.getString(param, "iv")));
        R r = R.ok().put("userInfo", miniService.getUserInfo(ParamResolvor.getString(param, "code"),
                ParamResolvor.getString(param, "encryptedData"),
                ParamResolvor.getString(param, "iv")));
        return r;
    }

    @RequestMapping("/getUserInfoBySessionKey")
    public R getUserInfoBySessionKey(@RequestBody Map<String, Object> param) {
        return R.ok().put("userInfo", miniService.getUserInfoBySessionKey(ParamResolvor.getString(param, "sessionKey"),
                ParamResolvor.getString(param, "encryptedData"),
                ParamResolvor.getString(param, "iv")));
    }

    @RequestMapping("/getPhoneNumberBySessionKey")
    public R getPhoneNumberBySessionKey(@RequestBody Map<String, Object> param) {
        return R.ok().put("phoneNumber", miniService.getUserInfoBySessionKey(ParamResolvor.getString(param, "sessionKey"),
                ParamResolvor.getString(param, "encryptedData"),
                ParamResolvor.getString(param, "iv")));
    }

    @RequestMapping("/sendTemplateMessage")
    public R sendTemplateMessage(@RequestBody Map<String, Object> param) {
        //openid: ohyWp5TA0t2SRg3KzWhsZ9OXdBw0
        miniService.sendTemplateMessage(
                ParamResolvor.getString(param, "toUser"),
                ParamResolvor.getString(param, "messageId"),
                "", "",
                ParamResolvor.getMap(param, "data"), "");
        return R.ok();
    }

    @RequestMapping("/sendUniformMessage2Cp")
    public R sendUniformMessage2Cp(@RequestBody Map<String, Object> param) {
        //openid: ohyWp5TA0t2SRg3KzWhsZ9OXdBw0
        miniService.sendUniformMessage2Cp(ParamResolvor.getString(param, "toUser"),
                ParamResolvor.getString(param, "messageId"),
                ParamResolvor.getMap(param, "data"), "", ""
        );
        return R.ok();
    }
}
