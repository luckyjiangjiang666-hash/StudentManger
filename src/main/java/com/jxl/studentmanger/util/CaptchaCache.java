package com.jxl.studentmanger.util;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CaptchaCache {
    //验证码缓存MAP
    private static ConcurrentHashMap<String,String> cache = new ConcurrentHashMap<>();

    // 存储验证码
    public void storeCaptcha(String key, String value) {
        cache.put(key, value);
    }

    // 验证验证码
    public boolean validateCaptcha(String key, String value) {
        String storedValue = cache.get(key);
        return storedValue != null && storedValue.equals(value);
    }

    // 移除验证码
    public void removeCaptcha(String key) {
        cache.remove(key);
    }

}