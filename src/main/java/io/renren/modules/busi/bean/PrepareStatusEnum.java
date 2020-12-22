package io.renren.modules.busi.bean;

/**
 * 报备状态
 */
public enum PrepareStatusEnum {
    WAIT_CHECKED(0, "报备", "审核中"),
    PREPARED(10, "重新报备", "拒收"),
    REJECT(-10, "无效", "拒收无效"),
    MANNUL_REJECT(-20, "无效", "手工无效"),
    EXPIRED(-30, "过期", "无效");

    private int code;
    private String label;
    private String title;

    private PrepareStatusEnum(int code, String label, String title) {
        this.code = code;
        this.label = label;
        this.title = title;
    }
}
