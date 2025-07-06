package com.xxliang.yunc.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xxliang
 * @date 2025/6/19  15:15
 * @description 远程调用拦截器
 */
@Slf4j
@Component
public class TokenFeignInterceptor implements RequestInterceptor {

    //请求头中的token
    private final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {

        log.info("feign远程调用拦截器");
        //1.获取请求对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        //2.从请求头获取到令牌
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        //3.把令牌添加到Fiegn的请求头
        requestTemplate.header(AUTHORIZATION_HEADER,authorization);

        log.info("feign远程调用拦截器结束{}",authorization);
    }

}