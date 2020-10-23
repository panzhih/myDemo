package com.test.demo.service;

import com.test.demo.common.Page;
import com.test.demo.model.User;

public interface UserService {

    //根据用户信息查询数据
    String selectByUser(User user, Page page, String token);
    //根据用户id删除数据
    String deleteByPrimaryKey(int id, String token);
    //根据用户信息修改数据
    String updateByUser(User user,String token);
    //新增用户信息
    String insertUser(User user);
    //登录接口
    String login(String username, String password);
}
