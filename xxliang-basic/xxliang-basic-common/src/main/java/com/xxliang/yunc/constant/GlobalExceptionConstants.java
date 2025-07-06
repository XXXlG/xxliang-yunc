package com.xxliang.yunc.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @author xxliang
 * @date 2025/5/30  11:19
 * @description 全局异常处理常量
 */
@Getter
public enum GlobalExceptionConstants {


    USER_NOT_NULL("用户不能为空", "10001"),
    PASSWORD_NOT_NULL("密码不能为空", "10002"),
    USER_NOT_EXIST("用户不存在", "10003"),
    USER_PASSWORD_ERROR("用户密码错误", "10004"),
    USER_NOT_LOGIN("用户未登录", "10005"),
    USER_NOT_ADMIN("用户不是管理员", "10006"),
    USER_NOT_EXIST_OR_PASSWORD_ERROR("用户不存在或密码错误", "10007"),
    USER_NOT_ACCESS("用户无权限,请联系管理员", "10008");


    private String message;
    private String code;

    /// 构造器
    /// @param message
    /// @param code
    GlobalExceptionConstants(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
