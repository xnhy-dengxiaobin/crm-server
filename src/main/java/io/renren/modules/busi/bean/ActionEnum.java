package io.renren.modules.busi.bean;

/**
 * 客户从报备到签约经历的不同的变更
 */
public enum ActionEnum {
    PREPARE(1, "报备"),
    RE_PREPARE(5, "重新报备"),
    EXPIRED(10, "过期"),
    VISITED(20, "来访"),
    MANNUL_AUDIT(30, "人工确客"),
    PROTECT_PERIOD_CHANGE(35, "保护期变更"),
    SOLICIT(40, "认筹"),
    TRANSCTION_VOID(45, "交易作废"),
    SUBSCRIBE(50, "认购"),
    SIGN(60, "签约");

    private int code;
    private String label;

    private ActionEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
