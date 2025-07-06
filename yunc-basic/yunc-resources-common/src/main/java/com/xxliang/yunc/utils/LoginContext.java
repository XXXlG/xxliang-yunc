package com.xxliang.yunc.utils;

import com.alibaba.fastjson.JSON;
import com.xxliang.yunc.domain.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author xxliang
 * @date 2025/6/3  12:43
 * @description 用户上下文
 */
@Slf4j
public class LoginContext {

    public static Login getLogin(){
        log.info("正在获取当前的用户信息。。。");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Login login = JSON.parseObject((String) authentication.getPrincipal(), Login.class);
        return login;
    }



}
