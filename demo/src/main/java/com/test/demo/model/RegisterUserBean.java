package com.test.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:注册用户数据
 */
@Data
@ApiModel(
        description = "注册用户数据"
)
public class RegisterUserBean {

    @ApiModelProperty(value = "名字(长度不能超过20个字符)",required = true)
    private String name;
    @ApiModelProperty(value = "密码(字母或数字构成，长度4~16位)",required = true)
    private String password;
    @ApiModelProperty(value = "年龄(1~200岁)",required = true)
    private Integer age;
    @ApiModelProperty(value = "介绍(长度不能超过100个字符)",required = true)
    private String introduction;
}
