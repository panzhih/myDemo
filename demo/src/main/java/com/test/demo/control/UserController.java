package com.test.demo.control;

import com.test.demo.common.Page;
import com.test.demo.model.QueryUserBean;
import com.test.demo.model.RegisterUserBean;
import com.test.demo.model.UpdateUserBean;
import com.test.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:用户测试类控制层
 */

@RestController
@RequestMapping(value = "user")
@Api(value = "测试模块", tags = "用户信息增删改查接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "login")
    @ApiOperation(value = "简陋的登录操作", notes = "传入用户密码信息")
    public String login(@ApiParam(value = "用户名", required = true) @RequestParam String username,
                        @ApiParam(value = "用户密码", required = true) @RequestParam String password){
        return this.userService.login(username,password);
    }

    @GetMapping(value = "getUserList")
    @ApiOperation(value = "获取用户信息", notes = "传入信息")
    public String getUser(@ApiParam(value = "用户信息", required = true) QueryUserBean user,
                          @ApiParam(value = "分页参数", required = true) Page page,
                          @ApiParam(value = "token", required = true) @RequestParam String token){
        return userService.selectByUser(user, page,token);
    }

    @GetMapping(value = "deleteUser/{id}/{token}")
    @ApiOperation(value = "根据用户id删除用户信息", notes = "传入用户id")
    public String deleteUser(@ApiParam(value = "用户id", required = true) @PathVariable(value = "id") int id,
                             @ApiParam(value = "token",required = true) @PathVariable(value = "token") String token){
        return userService.deleteByPrimaryKey(id,token);
    }

    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户信息", notes = "传入用户信息")
    public String updateUser(@ApiParam(value = "用户信息", required = true) UpdateUserBean user,
                             @ApiParam(value = "token", required = true) @RequestParam String token){
        return userService.updateByUser(user,token);
    }

    @RequestMapping(value = "updateUserPassword",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户登录密码", notes = "传入用户密码")
    public String updateUserPassword(@ApiParam(value = "用户id",required = true) @RequestParam Integer id,
                                     @ApiParam(value = "用户旧密码", required = true) @RequestParam String oldPassword,
                                     @ApiParam(value = "用户新密码(字母或数字构成，长度4~16位)",required = true) @RequestParam String newPassword,
                                     @ApiParam(value = "token", required = true) @RequestParam String token){
        return userService.updateUserPassword(id,oldPassword,newPassword,token);
    }

    @PostMapping(value = "insertUser")
    @ApiOperation(value = "注册用户信息", notes = "传入用户注册信息")
    public String insertUser(@ApiParam(value = "注册用户信息", required = true) RegisterUserBean user)  {
        return userService.insertUser(user);
    }
}
