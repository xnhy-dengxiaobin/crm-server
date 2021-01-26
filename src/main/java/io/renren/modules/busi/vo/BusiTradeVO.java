package io.renren.modules.busi.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusiTradeVO {
  private String buguid;
  private String tradeguid;
  //催收人
  private String cstname;
  //催收电话
  private String csttel;
  //房间详情
  private String roominfo;
  //项目类型
  private String producttype;
  //人民币成交金额
  private String rmbcjtotal;
  // 置业顾问
  private String ywy;
  //付款方式
  private String payformname;
  //建筑面积
  private String bldarea;
  //业务办理时间
  private String ywbldate;
  private String qsdate;
}
