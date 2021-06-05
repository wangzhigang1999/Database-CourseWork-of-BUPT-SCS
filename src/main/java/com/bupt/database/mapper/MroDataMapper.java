package com.bupt.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt.database.pojo.MroData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * (Photo)表数据库访问层
 *
 * @author wanz
 */
@Mapper
@Component
public interface MroDataMapper extends BaseMapper<MroData> {

}