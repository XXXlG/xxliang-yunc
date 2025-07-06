package com.xxliang.yunc.exception;

import com.xxliang.yunc.constant.GlobalExceptionConstants;
import com.xxliang.yunc.result.JSONResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

/**
 * @author xxliang
 * @date 2025/5/30  10:44
 * @description 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
/*
    @ExceptionHandler(Exception.class)
    public JSONResult handleException(Exception e) {
        //这个给前端会让前端懵逼的。
        e.printStackTrace();//仅仅适合自己打印排错。
        return JSONResult.error(e.getMessage(),"10000");
    }
*/


    /**
     * 截取JRE303异常处理的方法
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JSONResult validException(MethodArgumentNotValidException exception) {

        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError objectError : errors) {
            msg.append(objectError.getDefaultMessage()).append(","); //append的性能高于+切记。
        }
        return JSONResult.error(msg.toString(), "10010");
    }

    //捕获特殊异常。
    @ExceptionHandler(ArithmeticException.class)
    public JSONResult handleNullPointerException(Exception e) {
        return JSONResult.error(GlobalExceptionConstants.USER_NOT_EXIST);
    }


    @ExceptionHandler(GlobalBussnessException.class)
    public JSONResult handleGlobalBussnessExceptionException(GlobalBussnessException e) {
        return JSONResult.error(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
     public JSONResult handleException(Exception e) {
        if(e.getClass().getSimpleName().equals("MethodArgumentNotValidException")){
             MethodArgumentNotValidException exception = (MethodArgumentNotValidException)e;
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            StringBuilder msg = new StringBuilder();
            for (ObjectError objectError : errors) {
                msg.append(objectError.getDefaultMessage()).append(","); //append的性能高于+切记。
            }
            return JSONResult.error(msg.toString(), "10010");
        }
        if(e.getClass().getSimpleName().equals("AccessDeniedException")){
            return JSONResult.error(GlobalExceptionConstants.USER_NOT_ACCESS);
        }else {
             return JSONResult.error("异常联系管理员");
        }
    }
}
