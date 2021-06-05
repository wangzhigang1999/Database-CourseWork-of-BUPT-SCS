package com.bupt.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bupt.database.pojo.User;
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
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户名查询用户的信息
     *
     * @param userName 用户名
     * @return 用户的信息
     */
    @Select("select * from bupt.user where username=#{username}")
    User getUserByUserName(String userName);

    /**
     * 获取所有的用户信息
     *
     * @return 所有的用户
     */
    @Select("select * from bupt.user")
    List<User> getAllUsers();

}