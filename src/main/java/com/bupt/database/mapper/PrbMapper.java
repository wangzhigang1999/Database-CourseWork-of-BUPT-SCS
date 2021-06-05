package com.bupt.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt.database.pojo.Prb;
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
public interface PrbMapper extends  BaseMapper<Prb> {
    @Select("select  distinct  小区名 from prb;")
    List<String> getCommunity();
}