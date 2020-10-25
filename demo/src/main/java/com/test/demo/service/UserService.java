package com.test.demo.service;

import com.test.demo.common.Page;
import com.test.demo.model.QueryUserBean;
import com.test.demo.model.RegisterUserBean;
import com.test.demo.model.UpdateUserBean;

/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:应用层接口
 */
public interface UserService {

    //根据用户信息查询数据
    String selectByUser(QueryUserBean user, Page page, String token);
    //根据用户id删除数据
    String deleteByPrimaryKey(int id, String token);
    //根据用户信息修改数据
    String updateByUser(UpdateUserBean user, String token);
    //新增用户信息
    String insertUser(RegisterUserBean user);
    //登录接口
    String login(String username, String password);
    //修改用户密码
    String updateUserPassword(Integer id, String oldPassword, String newPassword, String token);
}
