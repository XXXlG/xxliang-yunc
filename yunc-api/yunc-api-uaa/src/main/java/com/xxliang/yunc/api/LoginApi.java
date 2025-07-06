package com.xxliang.yunc.api;

import com.xxliang.yunc.domain.Login;
import com.xxliang.yunc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xxliang
 * @date 2025/5/31  18:23
 * @description login的调用api
 */

@FeignClient (name = "service-uaa", path = "/login")
public interface LoginApi {

    /**
     * 登录API
     * @param login
     * @return
     */
    @RequestMapping(value="/phoneRegister",method= RequestMethod.POST)
    JSONResult phoneRegister(@RequestBody Login login);


    /**
     *  保存和修改公用的
     */
}
