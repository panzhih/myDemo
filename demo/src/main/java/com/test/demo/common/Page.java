package com.test.demo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:分页参数
 */
@Data
public class Page{
    @ApiModelProperty(value = "当前页起始行")
    private Integer current;
    @ApiModelProperty(value = "每页行数")
    private Integer size;
}
