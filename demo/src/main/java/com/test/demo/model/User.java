package com.test.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:用户实体类
 */
@Data
@ApiModel(
        description = "用户数据"
)
public class User implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键id",required = true)
    private Integer id;
    @ApiModelProperty(value = "名字",required = true)
    private String name;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "年龄",required = true)
    private Integer age;
    @ApiModelProperty(value = "介绍",required = true)
    private String introduction;
}
