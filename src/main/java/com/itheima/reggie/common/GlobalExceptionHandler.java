package com.itheima.reggie.common;

import com.itheima.reggie.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 张壮
 * @description 全局处理异常
 * @since 2023/2/25 14:57
 **/
@RestControllerAdvice // @RestControllerAdvice= @ControllerAdvice(annotations = {RestController.class, Controller.class}) + @ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     *  SQL语句执行异常
     * @param exception
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.error(exception.getMessage());
        if (exception.getMessage().contains("Duplicate entry")) {
            String[] split = exception.getMessage().split(" ");

            String msg = split[2] + "已存在";
           return Result.error(msg);
        }

        return Result.error("未知错误");
    }

    /**
     * 分类删除 异常
     * @param exception
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException exception) {
        log.error(exception.getMessage());
        return Result.error(exception.getMessage());
    }

}

