package com.test.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:更新用户属性
 */
@Data
@ApiModel(
        description = "更新用户属性"
)
public class UpdateUserBean {
    @ApiModelProperty(value = "主键id",required = true)
    private Integer id;
    @ApiModelProperty(value = "名字(长度不能超过20个字符)",required = true)
    private String name;
    @ApiModelProperty(value = "年龄(1~200岁)",required = true)
    private Integer age;
    @ApiModelProperty(value = "介绍(长度不能超过100个字符)",required = true)
    private String introduction;
}
