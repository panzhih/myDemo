package com.test.demo.dao;

import com.test.demo.common.Page;
import com.test.demo.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:数据库操作接口
 */
public interface UserMapper {

    //根据用户信息查询数据
    List<User> selectByUser(@Param("user") User user,@Param("page") Page page);
    //根据用户id删除数据
    int deleteByPrimaryKey(@Param("id") int id);
    //根据用户信息修改数据
    int updateByUser(User user);
    //新增用户信息
    int insertUser(User user);
    //查询其他用户同名记录
    List<User> selectUserByName(User user);
    //通过id获取记录
    User selectById(@Param("id") int id);
}
