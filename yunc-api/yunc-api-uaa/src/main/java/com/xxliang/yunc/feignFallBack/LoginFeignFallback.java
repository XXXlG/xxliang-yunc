package com.xxliang.yunc.feignFallBack;

import com.xxliang.yunc.api.LoginApi;
import com.xxliang.yunc.domain.Login;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.result.JSONResult;
import feign.hystrix.FallbackFactory;

/**
 * @author xxliang
 * @date 2025/5/31  19:03
 * @description 降级工厂
 */
public class LoginFeignFallback implements FallbackFactory<LoginApi> {
    @Override
    public LoginApi create(Throwable throwable) {
        return new LoginApi() {
            @Override
            public JSONResult phoneRegister(Login login) {
                throw new GlobalBussnessException("login调用失败！请联系管理员");
            }
        };
    }
}
