package com.test.demo.common;

/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:统一返回类型
 */
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

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return resultMessage;
    }

    public void setMsg(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
