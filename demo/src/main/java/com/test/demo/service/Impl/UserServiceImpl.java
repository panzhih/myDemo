package com.test.demo.service.Impl;

import com.test.demo.common.Page;
import com.test.demo.dao.UserMapper;
import com.test.demo.model.User;
import com.test.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectByUser(User user, Page page) {
        if(page.getCurrent() == null){
            page.setCurrent(0);
        }
        if(page.getSize() == null){
            page.setSize(10);
        }
        return userMapper.selectByUser(user, page);
    }
    @Override
    public int deleteByPrimaryKey(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }
    @Override
    public int updateByUser(User user) {
        return userMapper.updateByUser(user);
    }
    @Override
    public int insertUsers(List<User> userList) {
        return userMapper.insertUsers(userList);
    }

    @Override
    public Integer selectUserMaxId() {
        return this.userMapper.selectUserMaxId();
    }
}
