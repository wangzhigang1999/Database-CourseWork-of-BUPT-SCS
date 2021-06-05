package com.bupt.database.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanz
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Kpi {

    private String 起始时间;
    private String 网元基站名称;
    private String 小区;
    private String 小区名称;
    private String rrC连接建立完成次数;
    private String rrC连接请求次数;
    private String rrC建立成功率qf;
    private String e_RaB建立成功总次数;
    private String e_RaB建立尝试总次数;
    private String e_RaB建立成功率2;
    private String eNodeB触发的ERaB异常释放总次数;
    private String 小区切换出ERaB异常释放总次数;
    private String e_RaB掉线率;
    private String 无线接通率ay;
    private String eNodeB发起的S1ReseT导致的UeContext释放次数;
    private String ueContext异常释放次数;
    private String ueContext建立成功总次数;
    private String 无线掉线率;
    private String eNodeB内异频切换出成功次数;
    private String eNodeB内异频切换出尝试次数;
    private String eNodeB内同频切换出成功次数;
    private String eNodeB内同频切换出尝试次数;
    private String eNodeB间异频切换出成功次数;
    private String eNodeB间异频切换出尝试次数;
    private String eNodeB间同频切换出成功次数;
    private String eNodeB间同频切换出尝试次数;
    private String eNB内切换成功率;
    private String eNB间切换成功率;
    private String 同频切换成功率zsp;
    private String 异频切换成功率zsp;
    private String 切换成功率;
    private String 小区PdcP层所接收到的上行数据的总吞吐量;
    private String 小区PdcP层所发送的下行数据的总吞吐量;
    private String rrC重建请求次数;
    private String rrC连接重建比率;
    private String 通过重建回源小区的eNodeB间同频切换出执行成功次数;
    private String 通过重建回源小区的eNodeB间异频切换出执行成功次数;
    private String 通过重建回源小区的eNodeB内同频切换出执行成功次数;
    private String 通过重建回源小区的eNodeB内异频切换出执行成功次数;
    private String eNB内切换出成功次数;
    private String eNB内切换出请求次数;

}
