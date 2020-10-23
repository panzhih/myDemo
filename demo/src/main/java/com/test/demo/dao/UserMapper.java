package com.test.demo.dao;

import com.test.demo.common.Page;
import com.test.demo.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    //根据用户信息查询数据
    List<User> selectByUser(@Param("user") User user,@Param("page") Page page);
    //根据用户id删除数据
    int deleteByPrimaryKey(@Param("id") int id);
    //根据用户信息修改数据
    int updateByUser(User user);
    //新增用户信息
    int insertUsers(@Param("userList") List<User> user);
    //获取用户表最大值id
    Integer selectUserMaxId();
}
