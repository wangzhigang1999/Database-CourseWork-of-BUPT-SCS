package com.bupt.database.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cell {

  private String city;
  private String sectorId;
  private String sectorName;
  private String enodebid;
  private String enodebName;
  private String earfcn;
  private String pci;
  private String pss;
  private String sss;
  private String tac;
  private String vendor;
  private String longitude;
  private String latitude;
  private String style;
  private String azimuth;
  private String height;
  private String electtilt;
  private String mechtilt;
  private String totletilt;


}
