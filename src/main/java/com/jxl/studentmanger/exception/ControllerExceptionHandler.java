package com.jxl.studentmanger.exception;


import cn.dev33.satoken.exception.NotLoginException;
import com.jxl.studentmanger.response.R;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
//这是一个异常处理类，用于处理控制器中的异常捕获Controller的异常
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 业务异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public R businessExceptionHandler(BusinessException e) {
        return R.fail(e.getCode(), e.getMessage());
    }

    /**
     * 其他异常
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public R exceptionHandler(Exception e) {
        return R.fail(e.getMessage());
    }

    /**
     * 未登录异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotLoginException.class)
    public R loginExceptionHandler(NotLoginException e) {
        return R.fail(e.getCode(), e.getMessage());
    }
}