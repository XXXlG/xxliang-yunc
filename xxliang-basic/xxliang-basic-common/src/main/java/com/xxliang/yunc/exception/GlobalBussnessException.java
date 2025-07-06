package com.xxliang.yunc.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xxliang
 * @date 2025/5/30  10:42
 * @description 全局异常处理类
 */

public class GlobalBussnessException extends RuntimeException{
    public GlobalBussnessException() {
        super();
    }

    public GlobalBussnessException(String message) {
        super(message);
    }
}
