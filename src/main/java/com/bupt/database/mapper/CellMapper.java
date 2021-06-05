package com.bupt.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt.database.pojo.Cell;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (Photo)表数据库访问层
 *
 * @author wanz
 */
@Mapper
@Component
public interface CellMapper extends BaseMapper<Cell> {

    @Select("select  distinct  SECTOR_NAME from cell;")
    List<String> getCommunity();


    @Select("select distinct ENODEBID from cell")
    List<String> getEnodeBID();


    @Select("select distinct ENODEB_NAME from cell")
    List<String> getEnodeBName();

    @Select("select distinct SECTOR_ID from cell")
    List<String> getSECTORID();

    @Select("select distinct SECTOR_NAME from cell")
    List<String> getSECTOR_NAME();
}