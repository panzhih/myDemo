package com.test.demo.service.Impl;

import com.google.gson.Gson;
import com.test.demo.common.Common;
import com.test.demo.common.Page;
import com.test.demo.common.ResultData;
import com.test.demo.common.ResultEnum;
import com.test.demo.dao.UserMapper;
import com.test.demo.model.User;
import com.test.demo.service.UserService;
import com.test.demo.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String selectByUser(User user, Page page, String token) {
        Gson gson = new Gson();
        if (VerificationToken(token)) {
            return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
        }
        if(page.getCurrent() == null){
            page.setCurrent(0);
        }
        if(page.getSize() == null){
            page.setSize(10);
        }
        List<User> userList = userMapper.selectByUser(user, page);
        return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),userList));
    }
    @Override
    public String deleteByPrimaryKey(int id, String token) {
        Gson gson = new Gson();
        if (VerificationToken(token)) {
            return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
        }
        int result = userMapper.deleteByPrimaryKey(id);
        if(result > 0){
            return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),null));
        }
        return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
    }
    @Override
    public String updateByUser(User user,String token) {
        Gson gson = new Gson();
        if (VerificationToken(token)) {
            return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
        }
        if(user.getId()== null){
            return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
        }
        if(verificationOtherValue(user)){
            return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
        }
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        int result = userMapper.updateByUser(user);
        if(result > 0){
            return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),null));
        }
        return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
    }

    private boolean verificationOtherValue(User user) {
        if(StringUtils.isBlank(user.getName())){
            return true;
        }
        if(StringUtils.isBlank(user.getPassword())){
            return true;
        }
        if(StringUtils.isBlank(user.getIntroduction())){
            return true;
        }
        return user.getAge() == null;
    }

    @Override
    public String insertUser(User user) {
        Gson gson = new Gson();
        //验证其他属性
        if(verificationOtherValue(user)){
            return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
        }
        //验证名称
        if(verificationOtherName(user.getName())){
            return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
        }
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        int result = userMapper.insertUser(user);
        if(result > 0){
            return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),null));
        }
        return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
    }
    /*
    * 验证名称
    * name: 用户名
    * */
    private boolean verificationOtherName(String name) {
        if(StringUtils.isBlank(name)){
            return false;
        }
        User user = new User();
        user.setName(name);
        Page page = new Page();
        page.setCurrent(0);
        page.setSize(1);
        List<User> userList = this.userMapper.selectByUser(user,page);
        return userList != null;
    }

    @Override
    public String login(String username, String password) {
        Gson gson = new Gson();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
        }
        User user = new User();
        user.setPassword(MD5Util.getMD5(password));
        user.setName(username);
        Page page = new Page();
        page.setCurrent(0);
        page.setSize(1);
        //查询用户是否存在
        List<User> userList = this.userMapper.selectByUser(user,page);
        if(userList != null && userList.size() > 0){
            String token = MD5Util.getMD5(Common.TOKEN_SECRET+username);
            redisTemplate.opsForValue().set(token, token, Common.EXPIRE_TIME, TimeUnit.MILLISECONDS);
            return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),token));
        }
        return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
    }

    private Boolean VerificationToken(String token){
        return redisTemplate.opsForValue().getOperations().getExpire(token) < 1;
    }
}
