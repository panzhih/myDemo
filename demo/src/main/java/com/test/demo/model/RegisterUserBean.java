package com.test.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(
        description = "注册用户数据"
)
public class RegisterUserBean {

    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "年龄",example = "1")
    private Integer age;
    @ApiModelProperty(value = "介绍")
    private String introduction;
}
