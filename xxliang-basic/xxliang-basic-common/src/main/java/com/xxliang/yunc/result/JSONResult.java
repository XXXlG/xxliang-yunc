package com.xxliang.yunc.result;

import com.xxliang.yunc.constant.GlobalExceptionConstants;
import com.xxliang.yunc.exception.GlobalBussnessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//返回JSON结果
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSONResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success = true;

    private String message = "成功";

    //错误码，用来描述错误类型 ，20000 表示么有错误
    private String code = "20000";

    //返回的数据
    private Object data;

    /** 创建当前实例 **/
    public static JSONResult success(){
        return new JSONResult();
    }
    /** 创建当前实例 **/
    public static JSONResult success(Object obj){
        JSONResult instance = new JSONResult();
        instance.setData(obj);
        return instance;
    }

    public static JSONResult success(Object obj,String code){
        JSONResult instance = new JSONResult();
        instance.setCode(code);
        instance.setData(obj);
        return instance;
    }
    /** 创建当前实例 **/

    public static JSONResult error(String message,String code){
        JSONResult instance = new JSONResult();
        instance.setMessage(message);
        instance.setSuccess(false);
        instance.setCode(code);
        return instance;
    }

    public static JSONResult error(){
        JSONResult jsonResult = new JSONResult();
        jsonResult.setSuccess(false);
        return jsonResult;
    }

    /** 创建当前实例 **/
    public static JSONResult error(String message){
        return error(message,null);
    }


    public static JSONResult error(GlobalExceptionConstants error){
        return error(error.getMessage(),error.getCode());
    }


}
