package com.itheima.reggie.exception;

/**
 * @author 张壮
 * @description 自定义业务异常类
 * @since 2023/2/26 16:16
 **/
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}

