package com.test.demo.common;

import lombok.Data;

/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:统一返回类型
 */
@Data
public class ResultData {
    private Integer resultCode;
    private String resultMessage;
    private Long serverTime;
    private Object data;

    public ResultData(Integer resultCode, String resultMessage, Object data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.serverTime = System.currentTimeMillis();
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                ", serverTime=" + serverTime +
                ", data=" + data +
                '}';
    }
}
