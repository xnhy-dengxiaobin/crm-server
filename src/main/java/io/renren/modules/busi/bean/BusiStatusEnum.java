package io.renren.modules.busi.bean;

public enum BusiStatusEnum {
    VISITED(10, "来访");
    private int code;
    private String label;

    private BusiStatusEnum(int code, String label){
        this.code = code;
        this.label = label;
    }
}
