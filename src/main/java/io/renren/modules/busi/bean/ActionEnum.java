package io.renren.modules.busi.bean;

/**
 * 客户从报备到签约经历的不同的变更
 */
public enum ActionEnum {
    PREPARE(0, "报备"),
    RE_PREPARE(10, "重新报备"),
    EXPIRED(-30, "过期"),
    VISITED(-10, "来访"),
    MANNUL_AUDIT(-20, "人工确客"),
    PROTECT_PERIOD_CHANGE(-20, "保护期变更"),
    SOLICIT(-20, "认筹"),
    TRANSCTION_VOID(-20, "交易作废"),
    SUBSCRIBE(-20, "认购"),
    SIGN(-20, "签约");

    private int code;
    private String label;

    private ActionEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }
}
