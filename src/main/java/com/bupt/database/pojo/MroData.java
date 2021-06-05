package com.bupt.database.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wanz
 */
@Accessors
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MroData {

  private String timeStamp;
  private String servingSector;
  private String interferingSector;
  private String lteScRsrp;
  private String lteNcRsrp;
  private String lteNcEarfcn;
  private String lteNcPci;


}
