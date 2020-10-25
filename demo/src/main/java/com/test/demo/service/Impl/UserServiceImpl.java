package com.test.demo.service.Impl;

import com.google.gson.Gson;
import com.test.demo.common.Common;
import com.test.demo.common.Page;
import com.test.demo.common.ResultData;
import com.test.demo.common.ResultEnum;
import com.test.demo.dao.UserMapper;
import com.test.demo.model.QueryUserBean;
import com.test.demo.model.RegisterUserBean;
import com.test.demo.model.UpdateUserBean;
import com.test.demo.model.User;
import com.test.demo.service.UserService;
import com.test.demo.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:用户应用层实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户查询接口
     * @param user：查询的用户信息
     * @param page：分页参数
     * @param token：token唯一值
     * @return json格式数据
     */
    @Override
    public String selectByUser(QueryUserBean user, Page page, String token) {
        Gson gson = new Gson();
        //验证token
        if (VerificationToken(token)) {
            return gson.toJson(new ResultData(ResultEnum.TOKENERROR.getCode(),ResultEnum.TOKENERROR.getMsg(),null));
        }
        //设置初始查询分页参数
        if(page.getCurrent() == null){
            page.setCurrent(0);
        }
        if(page.getSize() == null){
            page.setSize(10);
        }
        User userOne = gson.fromJson(gson.toJson(user),User.class);
        List<User> userList = userMapper.selectByUser(userOne, page);
        return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),userList));
    }

    /**
     *用户删除接口
     * @param id：用户id
     * @param token:token唯一值
     * @return：json格式数据
     */
    @Override
    public String deleteByPrimaryKey(int id, String token) {
        Gson gson = new Gson();
        //验证token
        if (VerificationToken(token)) {
            return gson.toJson(new ResultData(ResultEnum.TOKENERROR.getCode(),ResultEnum.TOKENERROR.getMsg(),null));
        }
        int result = userMapper.deleteByPrimaryKey(id);
        if(result > 0){
            return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),null));
        }
        return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
    }

    /**
     *修改用户接口
     * @param user：修改的用户信息
     * @param token：token唯一值
     * @return json格式数据
     */
    @Override
    public String updateByUser(UpdateUserBean user, String token) {
        Gson gson = new Gson();
        //验证token
        if (VerificationToken(token)) {
            return gson.toJson(new ResultData(ResultEnum.TOKENERROR.getCode(),ResultEnum.TOKENERROR.getMsg(),null));
        }
        //验证id
        if(user.getId()== null){
            return gson.toJson(new ResultData(ResultEnum.PARAMERROR.getCode(),ResultEnum.PARAMERROR.getMsg(),null));
        }
        User userOne = gson.fromJson(gson.toJson(user),User.class);
        //验证其他属性值
        if(verificationOtherValue(userOne)){
            return gson.toJson(new ResultData(ResultEnum.PARAMERROR.getCode(),ResultEnum.PARAMERROR.getMsg(),null));
        }
        //验证用户名是否重复
        List<User> userList = this.userMapper.selectUserByName(userOne);
        if(userList != null && userList.size() > 0){
            return gson.toJson(new ResultData(ResultEnum.USERNAMEEXISTED.getCode(),ResultEnum.USERNAMEEXISTED.getMsg(),null));
        }
        int result = userMapper.updateByUser(userOne);
        if(result > 0){
            return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),null));
        }
        return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
    }

    /**
     * 新增用户接口
     * @param user：新增的用户信息
     * @return json格式数据
     */
    @Override
    public String insertUser(RegisterUserBean user) {
        Gson gson = new Gson();
        User userOne = gson.fromJson(gson.toJson(user),User.class);

        //验证其他属性
        if(verificationOtherValue(userOne)){
            return gson.toJson(new ResultData(ResultEnum.PARAMERROR.getCode(),ResultEnum.PARAMERROR.getMsg(),null));
        }
        //验证密码
        if(verificationPassword(userOne.getPassword())){
            return gson.toJson(new ResultData(ResultEnum.PASSWORDERROR.getCode(),ResultEnum.PASSWORDERROR.getMsg(),null));
        }
        //验证名称
        if(verificationUserName(userOne.getName())){
            return gson.toJson(new ResultData(ResultEnum.USERNAMEEXISTED.getCode(),ResultEnum.USERNAMEEXISTED.getMsg(),null));
        }
        userOne.setPassword(MD5Util.getMD5(userOne.getPassword()));
        int result = userMapper.insertUser(userOne);
        if(result > 0){
            return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),null));
        }
        return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
    }

    /**
     * 登录接口
     * @param username：用户名
     * @param password：用户密码
     * @return json格式数据
     */
    @Override
    public String login(String username, String password) {
        Gson gson = new Gson();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return gson.toJson(new ResultData(ResultEnum.PARAMERROR.getCode(),ResultEnum.PARAMERROR.getMsg(),null));
        }
        User user = new User();
        user.setPassword(MD5Util.getMD5(password));
        user.setName(username);
        Page page = new Page();
        page.setCurrent(0);
        page.setSize(1);
        //查询用户是否存在
        List<User> userList = this.userMapper.selectByUser(user,page);
        if(userList == null || userList.size() == 0){
            return gson.toJson(new ResultData(ResultEnum.USERNAMEORPASSWORDERROR.getCode(),ResultEnum.USERNAMEORPASSWORDERROR.getMsg(),null));
        }
        String token = MD5Util.getMD5(Common.TOKEN_SECRET+username);
        //设置token过期时间
        redisTemplate.opsForValue().set(token, token, Common.EXPIRE_TIME, TimeUnit.MILLISECONDS);
        return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),token));
    }

    /**
     * 修改用户密码
     * @param id：用户id
     * @param oldPassword:旧密码
     * @param newPassword：新密码
     * @param token：token唯一值
     * @return json格式数据
     */
    @Override
    public String updateUserPassword(Integer id, String oldPassword, String newPassword, String token) {
        Gson gson = new Gson();
        if(id == null || StringUtils.isBlank(oldPassword)
                || StringUtils.isBlank(newPassword)
                || StringUtils.isBlank(token)){
            return gson.toJson(new ResultData(ResultEnum.PARAMERROR.getCode(),ResultEnum.PARAMERROR.getMsg(),null));
        }
        //验证token
        if (VerificationToken(token)) {
            return gson.toJson(new ResultData(ResultEnum.TOKENERROR.getCode(),ResultEnum.TOKENERROR.getMsg(),null));
        }
        //验证密码
        if(verificationPassword(oldPassword) || verificationPassword(newPassword)){
            return gson.toJson(new ResultData(ResultEnum.PASSWORDERROR.getCode(),ResultEnum.PASSWORDERROR.getMsg(),null));
        }
        //验证旧密码
        User user = new User();
        user.setId(id);
        user.setPassword(MD5Util.getMD5(oldPassword));
        Page page = new Page();
        page.setCurrent(0);
        page.setSize(1);
        List<User> userList = this.userMapper.selectByUser(user,page);
        if(userList == null || userList.size() == 0){
            return gson.toJson(new ResultData(ResultEnum.USERNAMEERROR.getCode(),ResultEnum.USERNAMEERROR.getMsg(),null));
        }
        //修改新密码
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setPassword(MD5Util.getMD5(newPassword));
        int result = this.userMapper.updateByUser(updateUser);
        if(result > 0){
            return gson.toJson(new ResultData(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),null));
        }
        return gson.toJson(new ResultData(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),null));
    }

    /**
     * 验证token是否有效
     * @param token：token唯一值
     * @return Boolean(true-失效，false-有效)
     */
    private Boolean VerificationToken(String token){
        return redisTemplate.opsForValue().getOperations().getExpire(token) < 1;
    }

    /**
     * 验证用户名称
     * @param name：用户名
     * @return Boolean (true-存在，false-不存在)
     */
    private boolean verificationUserName(String name) {
        if(StringUtils.isBlank(name)){
            return false;
        }
        User user = new User();
        user.setName(name);
        Page page = new Page();
        page.setCurrent(0);
        page.setSize(1);
        List<User> userList = this.userMapper.selectByUser(user,page);
        return userList != null && userList.size() > 0;
    }

    /**
     * 验证用户其他属性
     * @param user：用户属性值
     * @return Boolean (true-验证失败，false-验证通过)
     */
    private boolean verificationOtherValue(User user) {
        //用户名为空或者用户名大于20个字符
        if(StringUtils.isBlank(user.getName()) || user.getName().length() > 20){
            return true;
        }
        //用户介绍不为空或者用户介绍大于100个字符
        if(StringUtils.isBlank(user.getIntroduction()) || user.getIntroduction().length() > 100){
            return true;
        }
        //用户年龄为空或者用户年龄在1~200岁
        return user.getAge() == null || user.getAge() <= 0 || user.getAge() > 200;
    }

    /**
     * 验证密码有效性
     * @param password:用户密码
     * @return Boolean (true-无效，false-验证通过)
     */
    private boolean verificationPassword(String password){
        if(StringUtils.isBlank(password)){
            return true;
        }
        Pattern p = Pattern.compile("[a-zA-Z0-9]{4,16}");
        Matcher m = p.matcher(password);
        boolean flag = m.matches();
        return !flag;
    }
}
