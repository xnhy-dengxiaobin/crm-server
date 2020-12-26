package io.renren.modules.busi.bean;

/**
 * 客户从报备到签约经历的不同的状态
 */
public enum BusiStatusEnum {
    PREPARE_AUDITING(0, "待确认"),
    PREPARE_REJECT(-10, "拒收无效"),
    PREPARE_MANNUL_REJECT(-20, "手工无效"),
    PREPARE_EXPIRED(-30, "过期无效"),
    PREPARE_OK(10, "有效"),

    CUS_PREPARED(20, "接收"),
    CUS_VISITED(30, "来访"),
    CUS_SOLICITED(40, "认筹"),
    CUS_SUBSCRIBED(50, "认购"),
    CUS_SUBSCRIBED_DISCARD(55, "认购作废"),
    CUS_SIGNED(60, "签约");

    private int code;
    private String label;

    private BusiStatusEnum(int code, String label) {
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
