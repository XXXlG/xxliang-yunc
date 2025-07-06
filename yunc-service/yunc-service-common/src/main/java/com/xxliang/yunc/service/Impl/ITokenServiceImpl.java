package com.xxliang.yunc.service.Impl;

import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.service.ITokenService;
import com.xxliang.yunc.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xxliang
 * @date 2025/6/21  16:18
 * @description
 */
@Service
public class ITokenServiceImpl implements ITokenService {

    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public String createToken(Long courseId) {
        //随机获取十六位的随机数 & uuid;
        String token = StrUtils.getRandomString(16);
        redisTemplate.opsForValue().set(ValidateConstant.REDIS_KEY_ORDER_TOKEN_KEY,token,10, TimeUnit.MINUTES); // k -token 10min 过期
        return token;
    }
}
