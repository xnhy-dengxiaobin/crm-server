package io.renren.modules.busi.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusiOrderVO {
  private Long orderId;
  private BigDecimal floorArea;
  private BigDecimal totalPricesDs;
  private String payForm;
  private String type;
  private String customerInfo;
}
