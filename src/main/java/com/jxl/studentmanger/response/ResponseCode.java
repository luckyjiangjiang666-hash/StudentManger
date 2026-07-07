package com.jxl.studentmanger.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    USERNAME_EXIST(1001, "用户存在"),
    /**
     * 生成验证码失败
     */
    CREATE_CAPTCHA_ERROR(2001, "生成验证码失败"),
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(1003, "验证码错误"),
    /**
     * 用户名或密码错误
     */
    USERNAME_USERPWD_ERROR(1002, "用户名密码错误");

    private Integer code;
    private String message;


}