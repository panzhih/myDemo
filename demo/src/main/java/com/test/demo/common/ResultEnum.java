package com.test.demo.common;
/**
 * @Auther: 潘志红
 * @Date: 2020/10/23
 * @Description:返回状态码信息
 */
public enum ResultEnum {

    SUCCESS(200,"成功"),

    USERNAMEERROR(1001,"用户不存在"),

    USERNAMEEXISTED(1002,"用户名已存在"),

    PASSWORDERROR(1003,"用户密码错误"),

    USERNAMEORPASSWORDERROR(1004,"用户名或密码异常"),

    PARAMERROR(1005,"参数异常"),

    TOKENERROR(1006,"token异常，请重新登录"),

    ERROR(500,"其他异常，请联系管理员");


    private int code;

    private String msg;

    ResultEnum(int code, String msg){
        this.code = code;

        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
