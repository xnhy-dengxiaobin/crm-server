package io.renren.modules.busi.utils;

import com.qiniu.util.Json;
import io.renren.common.utils.HttpUtils;
import io.renren.common.utils.JsonUtil;
import io.renren.common.utils.ParamResolvor;
import io.renren.modules.busi.bean.EnnParam;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {
    public static final String SYSTEM_NO = "enn";
    public static boolean isLogin = false;
    public static Map<String, Object> user = new HashMap<>();

    private String actionId;
    private String menuId;
    private String path;

    public DataUtil(String actionId, String menuId, String path) {
        this.actionId = actionId;
        this.menuId = menuId;
        this.path = path;
    }

    public DataUtil(String actionId, String menuId) {
        this(actionId, menuId, "");
    }

    private Map<String, String> mkParam(EnnParam p) throws Exception {
        p.put("actionId", this.actionId);
        p.put("menuId", this.menuId);
        p.put("path", this.path);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("systemNo", SYSTEM_NO);
        String metaStr = "";
        if (isLogin) {
            userInfo.put("sessionId", ParamResolvor.getString(user, "sessionId"));
            userInfo.put("userId", ParamResolvor.getString(user, "userId"));
            userInfo.put("userCode", ParamResolvor.getString(user, "userCode"));
            userInfo.put("userName", ParamResolvor.getString(user, "userName"));
            metaStr = JsonUtil.objectToJson(user);
        }
        p.put("userInfo", userInfo);
        p.put("systemNo", SYSTEM_NO);
        String paramStr = JsonUtil.objectToJson(p);

        Map<String, String> param = new HashMap<>();
        param.put("param", paramStr);
        param.put("meta", metaStr);
        return param;
    }

    public void login(String userCode, String password) throws Exception {
        EnnParam p = new EnnParam();
        p.put("userCode", userCode);
        p.put("password", password);
        Map<String, Object> result = rmi("http://localhost:6041/sync-server/sso/login", p);
        if (checkResult(result)) {
            user = ParamResolvor.getMap(result, "data");
            isLogin = true;
        } else {
            user = null;
            isLogin = false;
        }
    }

    private Map<String, Object> rmi(String url, EnnParam ennParam) throws Exception {
        Map<String, String> param = mkParam(ennParam);
        HttpResponse httpResponse = HttpUtils.doPost(url, param);
        Map<String, Object> result = HttpUtils.res2Map(httpResponse);
        return result;
    }

    public Map<String, Object> exec(EnnParam ennParam) throws Exception {
        return rmi("http://localhost:6041/sync-server/core/execBusiness", ennParam);
    }

    public boolean checkResult(Map<String, Object> result) {
        if (MapUtils.isEmpty(result) ||
                ParamResolvor.getInt(result, "code") != 1) {
            return false;
        } else {
            return true;
        }
    }
}
