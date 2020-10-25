package com.test.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:查询用户属性
 */
@Data
@ApiModel(
        description = "查询用户属性"
)
public class QueryUserBean {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "介绍")
    private String introduction;
}
