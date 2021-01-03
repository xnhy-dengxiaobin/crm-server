package io.renren.modules.busi.bean;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Enn框架参数
 */
public class EnnParam extends HashMap<String, Object>{
    private String path;
    private String actionId;
    private String menuId;
    private Integer buttonId;
    private List<String> datasetIds;
    private Map<String, Map<String, Object>> dataParam;
    private boolean hideLoading;
    private List<Map<String, Object>> datas;
    private String stepGroupId = "grv";
    private List<Map<String, Object>> steps;

    private Map<String, Object> userInfo;
    private String systemNo;

    public static EnnParam mkQueryParam(List<String> datasetIds, Map<String, Map<String, Object>> dataParam, boolean hideLoading) {
        EnnParam param = new EnnParam();
        param.put("datasetIds", datasetIds);
        param.put("dataParam", dataParam);
        param.put("hideLoading", hideLoading);
        return param;
    }

    public static EnnParam mkQueryParam(List<String> datasetIds, Map<String, Map<String, Object>> dataParam) {
        return mkQueryParam(datasetIds, dataParam, false);
    }

    public static EnnParam mkSaveParam(List<Map<String, Object>> datas, boolean hideLoading) {
        EnnParam param = new EnnParam();
        param.put("datas", datas);
        param.put("hideLoading", false);
        return param;
    }

    public static EnnParam mkSaveParam(List<Map<String, Object>> datas) {
        return mkSaveParam(datas, false);
    }

    public static EnnParam mkExecParam(List<Step> steps,
                                       Map<String, Map<String, Object>> dataParam,
                                       List<Map<String, Object>> datas, boolean hideLoading) {
        EnnParam param = new EnnParam();
        param.put("steps", steps);
        param.put("datas", datas);
        param.put("dataParam", dataParam);
        param.put("hideLoading", hideLoading);
        param.put("buttonId", 3);
        return param;
    }

    public static EnnParam mkExecParam(
            List<Step> steps,
            Map<String, Map<String, Object>> dataParam) {
        return mkExecParam(steps, dataParam, null, false);
    }

    public static EnnParam mkExecParam(List<Step> steps) {
       return mkExecParam(steps, null, null, false);
    }

    public static List<Step> mkSteps(Step... stepList) {
        List<Step> steps = Arrays.asList(stepList);
        return steps;
    }

    @Data
    public static final class Step {
        private String type = "";
        private String name = "-1";

        public Step(String type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}
