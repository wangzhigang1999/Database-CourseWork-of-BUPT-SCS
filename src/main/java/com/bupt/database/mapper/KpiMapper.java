package com.bupt.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt.database.pojo.Kpi;
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
public interface KpiMapper extends BaseMapper<Kpi> {

    @Select("select * from kpi limit #{limit} offset #{offset}")
    List<Kpi> selectRange(int limit, int offset);

    @Select("select  distinct  小区名称 from kpi;")
    List<String> getCommunity();

}