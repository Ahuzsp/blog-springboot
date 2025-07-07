package com.ums.exception;

import com.ums.common.CommonResult;
import com.ums.interceptors.UnauthorizedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    // 捕获自定义未授权异常
    @ExceptionHandler(UnauthorizedException.class)
    public CommonResult<String> handleUnauthorizedException(UnauthorizedException e) {
        return CommonResult.unauthorized(e.getMessage());
    }

    // 捕获其他异常
    @ExceptionHandler(Exception.class)
    public CommonResult<String> handleAllExceptions(Exception e) {
        e.printStackTrace();
        return CommonResult.failed("服务器内部错误，" + e.getMessage());
    }
}
