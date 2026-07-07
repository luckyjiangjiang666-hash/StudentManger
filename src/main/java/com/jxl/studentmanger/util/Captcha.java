package com.jxl.studentmanger.util;

import lombok.Data;

@Data
public class Captcha {
    //验证码标识
    private String captchaId;
    //验证码图片
    private String captchaImage;
}
