package com.test.demo.service;

import com.test.demo.common.Page;
import com.test.demo.model.User;

import java.util.List;

public interface UserService {

    //根据用户信息查询数据
    List<User> selectByUser(User user, Page page);
    //根据用户id删除数据
    int deleteByPrimaryKey(int id);
    //根据用户信息修改数据
    int updateByUser(User user);
    //新增用户信息
    int insertUsers(List<User> user);
    //获取最大值id
    Integer selectUserMaxId();
}
