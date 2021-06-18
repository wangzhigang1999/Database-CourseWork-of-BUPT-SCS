package com.bupt.database.util;

import com.bupt.database.mapper.CellMapper;
import com.bupt.database.mapper.KpiMapper;
import com.bupt.database.mapper.MroDataMapper;
import com.bupt.database.mapper.PrbMapper;
import com.bupt.database.pojo.Cell;
import com.bupt.database.pojo.Kpi;
import com.bupt.database.pojo.Prb;
import com.monitorjbl.xlsx.StreamingReader;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanz
 */
@Component
public class XlsxInsert {
    final CellMapper cellMapper;
    final KpiMapper kpiMapper;
    final MroDataMapper mroDataMapper;
    final PrbMapper prbMapper;


    public XlsxInsert(CellMapper cellMapper, KpiMapper kpiMapper, MroDataMapper mroDataMapper, PrbMapper prbMapper) {
        this.cellMapper = cellMapper;
        this.kpiMapper = kpiMapper;
        this.mroDataMapper = mroDataMapper;
        this.prbMapper = prbMapper;
    }

    public Map<String, Integer> insert(String tableName, String filePath) {
        return switch (tableName) {
            case "cell" -> doCell(filePath);
            case "kpi" -> doKpi(filePath);
            case "prb" -> doPrb(filePath);
            default -> null;
        };
    }

    @SneakyThrows
    private Map<String, Integer> doPrb(String filePath) {
        FileInputStream in = new FileInputStream(filePath);
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)
                .bufferSize(4096)
                .open(in);
        Sheet sheet = wk.getSheetAt(0);
        int cnt = 0;
        int successCnt = 0;
        int failCnt = 0;
        for (Row row : sheet) {
            if (cnt == 0) {
                cnt++;
                continue;
            }
            Prb prb = new Prb();
            prb.set起始时间(String.valueOf(row.getCell(0).getStringCellValue()));
            prb.set网元_基站名称(String.valueOf(row.getCell(1).getStringCellValue()));
            prb.set小区描述(String.valueOf(row.getCell(2).getStringCellValue()));
            prb.set小区名(String.valueOf(row.getCell(3).getStringCellValue()));
            prb.setidx_0(Double.parseDouble(String.valueOf(row.getCell(4).getStringCellValue())));
            prb.setidx_1(Double.parseDouble(String.valueOf(row.getCell(5).getStringCellValue())));
            prb.setidx_2(Double.parseDouble(String.valueOf(row.getCell(6).getStringCellValue())));
            prb.setidx_3(Double.parseDouble(String.valueOf(row.getCell(7).getStringCellValue())));
            prb.setidx_4(Double.parseDouble(String.valueOf(row.getCell(8).getStringCellValue())));
            prb.setidx_5(Double.parseDouble(String.valueOf(row.getCell(9).getStringCellValue())));
            prb.setidx_6(Double.parseDouble(String.valueOf(row.getCell(10).getStringCellValue())));
            prb.setidx_7(Double.parseDouble(String.valueOf(row.getCell(11).getStringCellValue())));
            prb.setidx_8(Double.parseDouble(String.valueOf(row.getCell(12).getStringCellValue())));
            prb.setidx_9(Double.parseDouble(String.valueOf(row.getCell(13).getStringCellValue())));
            prb.setidx_10(Double.parseDouble(String.valueOf(row.getCell(14).getStringCellValue())));
            prb.setidx_11(Double.parseDouble(String.valueOf(row.getCell(15).getStringCellValue())));
            prb.setidx_12(Double.parseDouble(String.valueOf(row.getCell(16).getStringCellValue())));
            prb.setidx_13(Double.parseDouble(String.valueOf(row.getCell(17).getStringCellValue())));
            prb.setidx_14(Double.parseDouble(String.valueOf(row.getCell(18).getStringCellValue())));
            prb.setidx_15(Double.parseDouble(String.valueOf(row.getCell(19).getStringCellValue())));
            prb.setidx_16(Double.parseDouble(String.valueOf(row.getCell(20).getStringCellValue())));
            prb.setidx_17(Double.parseDouble(String.valueOf(row.getCell(21).getStringCellValue())));
            prb.setidx_18(Double.parseDouble(String.valueOf(row.getCell(22).getStringCellValue())));
            prb.setidx_19(Double.parseDouble(String.valueOf(row.getCell(23).getStringCellValue())));
            prb.setidx_20(Double.parseDouble(String.valueOf(row.getCell(24).getStringCellValue())));
            prb.setidx_21(Double.parseDouble(String.valueOf(row.getCell(25).getStringCellValue())));
            prb.setidx_22(Double.parseDouble(String.valueOf(row.getCell(26).getStringCellValue())));
            prb.setidx_23(Double.parseDouble(String.valueOf(row.getCell(27).getStringCellValue())));
            prb.setidx_24(Double.parseDouble(String.valueOf(row.getCell(28).getStringCellValue())));
            prb.setidx_25(Double.parseDouble(String.valueOf(row.getCell(29).getStringCellValue())));
            prb.setidx_26(Double.parseDouble(String.valueOf(row.getCell(30).getStringCellValue())));
            prb.setidx_27(Double.parseDouble(String.valueOf(row.getCell(31).getStringCellValue())));
            prb.setidx_28(Double.parseDouble(String.valueOf(row.getCell(32).getStringCellValue())));
            prb.setidx_29(Double.parseDouble(String.valueOf(row.getCell(33).getStringCellValue())));
            prb.setidx_30(Double.parseDouble(String.valueOf(row.getCell(34).getStringCellValue())));
            prb.setidx_31(Double.parseDouble(String.valueOf(row.getCell(35).getStringCellValue())));
            prb.setidx_32(Double.parseDouble(String.valueOf(row.getCell(36).getStringCellValue())));
            prb.setidx_33(Double.parseDouble(String.valueOf(row.getCell(37).getStringCellValue())));
            prb.setidx_34(Double.parseDouble(String.valueOf(row.getCell(38).getStringCellValue())));
            prb.setidx_35(Double.parseDouble(String.valueOf(row.getCell(39).getStringCellValue())));
            prb.setidx_36(Double.parseDouble(String.valueOf(row.getCell(40).getStringCellValue())));
            prb.setidx_37(Double.parseDouble(String.valueOf(row.getCell(41).getStringCellValue())));
            prb.setidx_38(Double.parseDouble(String.valueOf(row.getCell(42).getStringCellValue())));
            prb.setidx_39(Double.parseDouble(String.valueOf(row.getCell(43).getStringCellValue())));
            prb.setidx_40(Double.parseDouble(String.valueOf(row.getCell(44).getStringCellValue())));
            prb.setidx_41(Double.parseDouble(String.valueOf(row.getCell(45).getStringCellValue())));
            prb.setidx_42(Double.parseDouble(String.valueOf(row.getCell(46).getStringCellValue())));
            prb.setidx_43(Double.parseDouble(String.valueOf(row.getCell(47).getStringCellValue())));
            prb.setidx_44(Double.parseDouble(String.valueOf(row.getCell(48).getStringCellValue())));
            prb.setidx_45(Double.parseDouble(String.valueOf(row.getCell(49).getStringCellValue())));
            prb.setidx_46(Double.parseDouble(String.valueOf(row.getCell(50).getStringCellValue())));
            prb.setidx_47(Double.parseDouble(String.valueOf(row.getCell(51).getStringCellValue())));
            prb.setidx_48(Double.parseDouble(String.valueOf(row.getCell(52).getStringCellValue())));
            prb.setidx_49(Double.parseDouble(String.valueOf(row.getCell(53).getStringCellValue())));
            prb.setidx_50(Double.parseDouble(String.valueOf(row.getCell(54).getStringCellValue())));
            prb.setidx_51(Double.parseDouble(String.valueOf(row.getCell(55).getStringCellValue())));
            prb.setidx_52(Double.parseDouble(String.valueOf(row.getCell(56).getStringCellValue())));
            prb.setidx_53(Double.parseDouble(String.valueOf(row.getCell(57).getStringCellValue())));
            prb.setidx_54(Double.parseDouble(String.valueOf(row.getCell(58).getStringCellValue())));
            prb.setidx_55(Double.parseDouble(String.valueOf(row.getCell(59).getStringCellValue())));
            prb.setidx_56(Double.parseDouble(String.valueOf(row.getCell(60).getStringCellValue())));
            prb.setidx_57(Double.parseDouble(String.valueOf(row.getCell(61).getStringCellValue())));
            prb.setidx_58(Double.parseDouble(String.valueOf(row.getCell(62).getStringCellValue())));
            prb.setidx_59(Double.parseDouble(String.valueOf(row.getCell(63).getStringCellValue())));
            prb.setidx_60(Double.parseDouble(String.valueOf(row.getCell(64).getStringCellValue())));
            prb.setidx_61(Double.parseDouble(String.valueOf(row.getCell(65).getStringCellValue())));
            prb.setidx_62(Double.parseDouble(String.valueOf(row.getCell(66).getStringCellValue())));
            prb.setidx_63(Double.parseDouble(String.valueOf(row.getCell(67).getStringCellValue())));
            prb.setidx_64(Double.parseDouble(String.valueOf(row.getCell(68).getStringCellValue())));
            prb.setidx_65(Double.parseDouble(String.valueOf(row.getCell(69).getStringCellValue())));
            prb.setidx_66(Double.parseDouble(String.valueOf(row.getCell(70).getStringCellValue())));
            prb.setidx_67(Double.parseDouble(String.valueOf(row.getCell(71).getStringCellValue())));
            prb.setidx_68(Double.parseDouble(String.valueOf(row.getCell(72).getStringCellValue())));
            prb.setidx_69(Double.parseDouble(String.valueOf(row.getCell(73).getStringCellValue())));
            prb.setidx_70(Double.parseDouble(String.valueOf(row.getCell(74).getStringCellValue())));
            prb.setidx_71(Double.parseDouble(String.valueOf(row.getCell(75).getStringCellValue())));
            prb.setidx_72(Double.parseDouble(String.valueOf(row.getCell(76).getStringCellValue())));
            prb.setidx_73(Double.parseDouble(String.valueOf(row.getCell(77).getStringCellValue())));
            prb.setidx_74(Double.parseDouble(String.valueOf(row.getCell(78).getStringCellValue())));
            prb.setidx_75(Double.parseDouble(String.valueOf(row.getCell(79).getStringCellValue())));
            prb.setidx_76(Double.parseDouble(String.valueOf(row.getCell(80).getStringCellValue())));
            prb.setidx_77(Double.parseDouble(String.valueOf(row.getCell(81).getStringCellValue())));
            prb.setidx_78(Double.parseDouble(String.valueOf(row.getCell(82).getStringCellValue())));
            prb.setidx_79(Double.parseDouble(String.valueOf(row.getCell(83).getStringCellValue())));
            prb.setidx_80(Double.parseDouble(String.valueOf(row.getCell(84).getStringCellValue())));
            prb.setidx_81(Double.parseDouble(String.valueOf(row.getCell(85).getStringCellValue())));
            prb.setidx_82(Double.parseDouble(String.valueOf(row.getCell(86).getStringCellValue())));
            prb.setidx_83(Double.parseDouble(String.valueOf(row.getCell(87).getStringCellValue())));
            prb.setidx_84(Double.parseDouble(String.valueOf(row.getCell(88).getStringCellValue())));
            prb.setidx_85(Double.parseDouble(String.valueOf(row.getCell(89).getStringCellValue())));
            prb.setidx_86(Double.parseDouble(String.valueOf(row.getCell(90).getStringCellValue())));
            prb.setidx_87(Double.parseDouble(String.valueOf(row.getCell(91).getStringCellValue())));
            prb.setidx_88(Double.parseDouble(String.valueOf(row.getCell(92).getStringCellValue())));
            prb.setidx_89(Double.parseDouble(String.valueOf(row.getCell(93).getStringCellValue())));
            prb.setidx_90(Double.parseDouble(String.valueOf(row.getCell(94).getStringCellValue())));
            prb.setidx_91(Double.parseDouble(String.valueOf(row.getCell(95).getStringCellValue())));
            prb.setidx_92(Double.parseDouble(String.valueOf(row.getCell(96).getStringCellValue())));
            prb.setidx_93(Double.parseDouble(String.valueOf(row.getCell(97).getStringCellValue())));
            prb.setidx_94(Double.parseDouble(String.valueOf(row.getCell(98).getStringCellValue())));
            prb.setidx_95(Double.parseDouble(String.valueOf(row.getCell(99).getStringCellValue())));
            prb.setidx_96(Double.parseDouble(String.valueOf(row.getCell(100).getStringCellValue())));
            prb.setidx_97(Double.parseDouble(String.valueOf(row.getCell(101).getStringCellValue())));
            prb.setidx_98(Double.parseDouble(String.valueOf(row.getCell(102).getStringCellValue())));
            prb.setidx_99(Double.parseDouble(String.valueOf(row.getCell(103).getStringCellValue())));

            try {
                prbMapper.insert(prb);

                successCnt++;
            } catch (Exception e) {
                failCnt++;
            }

        }

        HashMap<String, Integer> map = new HashMap<>(16);
        map.put("success", successCnt);
        map.put("fail", failCnt);
        map.put("total", successCnt + failCnt);
        return map;
    }

    @SneakyThrows
    private Map<String, Integer> doKpi(String filePath) {
        FileInputStream in = new FileInputStream(filePath);
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)
                .bufferSize(4096)
                .open(in);
        Sheet sheet = wk.getSheetAt(0);
        int successCnt = 0;
        int failCnt = 0;

        for (Row row : sheet) {
            Kpi kpi = new Kpi(
                    String.valueOf(row.getCell(0).getStringCellValue()),
                    String.valueOf(row.getCell(1).getStringCellValue()),
                    String.valueOf(row.getCell(2).getStringCellValue()),
                    String.valueOf(row.getCell(3).getStringCellValue()),
                    String.valueOf(row.getCell(4).getStringCellValue()),
                    String.valueOf(row.getCell(5).getStringCellValue()),
                    String.valueOf(row.getCell(6).getStringCellValue()),
                    String.valueOf(row.getCell(7).getStringCellValue()),
                    String.valueOf(row.getCell(8).getStringCellValue()),
                    String.valueOf(row.getCell(9).getStringCellValue()),
                    String.valueOf(row.getCell(10).getStringCellValue()),
                    String.valueOf(row.getCell(11).getStringCellValue()),
                    String.valueOf(row.getCell(12).getStringCellValue()),
                    String.valueOf(row.getCell(13).getStringCellValue()),
                    String.valueOf(row.getCell(14).getStringCellValue()),
                    String.valueOf(row.getCell(15).getStringCellValue()),
                    String.valueOf(row.getCell(16).getStringCellValue()),
                    String.valueOf(row.getCell(17).getStringCellValue()),
                    String.valueOf(row.getCell(18).getStringCellValue()),
                    String.valueOf(row.getCell(19).getStringCellValue()),
                    String.valueOf(row.getCell(20).getStringCellValue()),
                    String.valueOf(row.getCell(21).getStringCellValue()),
                    String.valueOf(row.getCell(22).getStringCellValue()),
                    String.valueOf(row.getCell(23).getStringCellValue()),
                    String.valueOf(row.getCell(24).getStringCellValue()),
                    String.valueOf(row.getCell(25).getStringCellValue()),
                    String.valueOf(row.getCell(26).getStringCellValue()),
                    String.valueOf(row.getCell(27).getStringCellValue()),
                    String.valueOf(row.getCell(28).getStringCellValue()),
                    String.valueOf(row.getCell(29).getStringCellValue()),
                    String.valueOf(row.getCell(30).getStringCellValue()),
                    String.valueOf(row.getCell(31).getStringCellValue()),
                    String.valueOf(row.getCell(32).getStringCellValue()),
                    String.valueOf(row.getCell(33).getStringCellValue()),
                    String.valueOf(row.getCell(34).getStringCellValue()),
                    String.valueOf(row.getCell(35).getStringCellValue()),
                    String.valueOf(row.getCell(36).getStringCellValue()),
                    String.valueOf(row.getCell(37).getStringCellValue()),
                    String.valueOf(row.getCell(38).getStringCellValue()),
                    String.valueOf(row.getCell(39).getStringCellValue()),
                    String.valueOf(row.getCell(40).getStringCellValue())
            );
            try {
                kpiMapper.insert(kpi);
                successCnt++;
            } catch (Exception e) {
                e.printStackTrace();
                failCnt++;
            }

        }


        HashMap<String, Integer> map = new HashMap<>(16);
        map.put("success", successCnt);
        map.put("fail", failCnt);
        map.put("total", successCnt + failCnt);
        return map;
    }


    @SneakyThrows
    private Map<String, Integer> doCell(String filePath) {
        FileInputStream in = new FileInputStream(filePath);
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(5000)
                .bufferSize(4096)
                .open(in);
        Sheet sheet = wk.getSheetAt(0);
        int successCnt = 0;
        int failCnt = 0;
        int cnt = 0;
        for (Row row : sheet) {
            cnt++;
            System.out.println("++++++++++++++++++++++++++++++++++++++++++");
            Cell cell = new Cell(
                    String.valueOf(row.getCell(0).getStringCellValue()),
                    String.valueOf(row.getCell(1).getStringCellValue()),
                    String.valueOf(row.getCell(2).getStringCellValue()),
                    String.valueOf(row.getCell(3).getStringCellValue()),
                    String.valueOf(row.getCell(4).getStringCellValue()),
                    String.valueOf(row.getCell(5).getStringCellValue()),
                    String.valueOf(row.getCell(6).getStringCellValue()),
                    String.valueOf(row.getCell(7).getStringCellValue()),
                    String.valueOf(row.getCell(8).getStringCellValue()),
                    String.valueOf(row.getCell(9).getStringCellValue()),
                    String.valueOf(row.getCell(10).getStringCellValue()),
                    String.valueOf(row.getCell(11).getStringCellValue()),
                    String.valueOf(row.getCell(12).getStringCellValue()),
                    String.valueOf(row.getCell(13).getStringCellValue()),
                    String.valueOf(row.getCell(14).getStringCellValue()),
                    String.valueOf(row.getCell(15).getStringCellValue()),
                    String.valueOf(row.getCell(16).getStringCellValue()),
                    String.valueOf(row.getCell(17).getStringCellValue()),
                    String.valueOf(row.getCell(18).getStringCellValue())
            );
            try {

                // 数据清洗, 按照经纬度,根据中国的经纬度进行判断
                var latitude = Float.parseFloat(cell.getLatitude());
                var longitude = Float.parseFloat(cell.getLongitude());

                if ((longitude > 54) || (latitude > 140)) {
                    failCnt++;
                    try (PrintWriter writer = new PrintWriter(new FileWriter("log.txt", true))) {
                        writer.println(cnt);
                    } catch (IOException e) {
                        continue;
                    }
                    continue;
                }


                // 数据清洗,如果数据存在,将其删除然后再重新插入新数据
                HashMap<String, Object> map = new HashMap<>();
                map.put("SECTOR_ID", cell.getSectorId());
                List<Cell> list = cellMapper.selectByMap(map);
                if (list.size() != 0) {
                    cellMapper.deleteByMap(map);
                }
                cellMapper.insert(cell);
                successCnt++;
            } catch (Exception e) {
                failCnt++;
            }

        }

        HashMap<String, Integer> map = new HashMap<>(16);
        map.put("success", successCnt);
        map.put("fail", failCnt);
        map.put("total", successCnt + failCnt);
        return map;
    }

}