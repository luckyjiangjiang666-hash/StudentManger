package com.jxl.studentmanger.exception;

import com.jxl.studentmanger.response.ResponseCode;
import lombok.Data;


@Data
public class BusinessException extends RuntimeException{
    private Integer code;
    private String message;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }
}
