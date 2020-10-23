package com.test.demo.control;

import com.test.demo.common.Page;
import com.test.demo.model.User;
import com.test.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "user")
@Api(value = "测试模块", tags = "用户信息增删改查接口")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "getUserList")
    @ApiOperation(value = "获取用户信息", notes = "传入信息")
    @ResponseBody
    public List<User> getUser(@ApiParam(value = "用户信息", required = true) User user,
                              @ApiParam(value = "分页参数", required = true) Page page){
        return userService.selectByUser(user, page);
    }

    @GetMapping(value = "deleteUser/{id}")
    @ApiOperation(value = "根据用户id删除用户信息", notes = "传入用户id")
    @ResponseBody
    public int deleteUser(@ApiParam(value = "用户id", required = true) @PathVariable(value = "id") int id){
        return userService.deleteByPrimaryKey(id);
    }

    @RequestMapping(value = "updateByUser",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户信息", notes = "传入用户信息")
    @ResponseBody
    public int updateByUser(@ApiParam(value = "用户信息", required = true) User user){
        return userService.updateByUser(user);
    }

    @GetMapping(value = "insertUsers")
    @ApiOperation(value = "新增用户信息", notes = "目前不需要传入参数，自动生成")
    @ResponseBody
    public int insertUsers(){
        //获取用户表id最大值
        Integer id = this.userService.selectUserMaxId();
        if(id == null){
            id = 0;
        }
        List<User> userList = new ArrayList<>();
        for(int i = id+1 ; i <= id+10 ; i ++){
            User user = new User();
            user.setName("用户"+i);
            user.setAge(i+1);
            user.setIntroduction("我是"+user.getName());
            userList.add(user);
        }
        return userService.insertUsers(userList);
    }
}
