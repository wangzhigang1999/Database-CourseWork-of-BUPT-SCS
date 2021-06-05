# -*- coding: utf-8 -*-
import pandas as pd

from query import connect

res =  ("起始时间", "网元基站名称", "小区", "小区名称", "RRC连接建立完成次数", "RRC连接请求次数", "RRC建立成功率qf", "E-RAB建立成功总次数", "E-RAB建立尝试总次数", "E-RAB建立成功率2", "eNodeB触发的E_RAB异常释放总次数", "小区切换出E_RAB异常释放总次数", "E-RAB掉线率", "无线接通率ay", "eNodeB发起的S1RESET导致的UEContext释放次数", "UEContext异常释放次数", "UEContext建立成功总次数", "无线掉线率", "eNodeB内异频切换出成功次数", "eNodeB内异频切换出尝试次数", "eNodeB内同频切换出成功次数", "eNodeB内同频切换出尝试次数", "eNodeB间异频切换出成功次数", "eNodeB间异频切换出尝试次数", "eNodeB间同频切换出成功次数", "eNodeB间同频切换出尝试次数", "eNB内切换成功率", "eNB间切换成功率", "同频切换成功率zsp", "异频切换成功率zsp", "切换成功率", "小区PDCP层所接收到的上行数据的总吞吐量", "小区PDCP层所发送的下行数据的总吞吐量", "RRC重建请求次数", "RRC连接重建比率", "通过重建回源小区的eNodeB间同频切换出执行成功次数", "通过重建回源小区的eNodeB间异频切换出执行成功次数", "通过重建回源小区的eNodeB内同频切换出执行成功次数", "通过重建回源小区的eNodeB内异频切换出执行成功次数", "eNB内切换出成功次数", "eNB内切换出请求次数")
tb_name = "kpi"

con = connect()

tbC2I3 = pd.DataFrame(columns=res)

tbC2I3.to_sql(tb_name, con, if_exists='replace', index=False)


