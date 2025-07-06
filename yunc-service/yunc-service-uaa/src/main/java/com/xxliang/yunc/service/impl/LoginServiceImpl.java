package com.xxliang.yunc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xxliang.yunc.domain.Login;
import com.xxliang.yunc.dto.LoginDto;
import com.xxliang.yunc.dto.RefreshTokenDto;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.mapper.LoginMapper;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.service.ILoginService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xxliang.yunc.util.HttpUtil;
import com.xxliang.yunc.vo.AccessTokenVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 登录表 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
@Service
@SuppressWarnings("all")
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {


    @Value("${ymcc.authUrl}")
    String authUrl;

    @Value("${ymcc.refreshUrl}")
    String refreshUrl;
    /**
     * 授权Token
     * @param loginDto
     * @return
     */
    @Override
    public AccessTokenVo common(LoginDto loginDto) {
        //是不是要先登录？

        //根据用户名查询用户信息。
        Wrapper<Login> wrapper = new EntityWrapper<>();
        wrapper.eq("username",loginDto.getUsername());
        Login login = selectOne(wrapper);
        //校验type，如 0为系统用户，1为用户。
        if(loginDto.getType().intValue()!=login.getType().intValue()){
            throw new GlobalBussnessException("用户类型错误");
        }
        //请求Token
        authUrl = String.format(authUrl,login.getClientId(),login.getClientSecret(),loginDto.getUsername(),loginDto.getPassword());
        String s = HttpUtil.sendPost(authUrl,  null);
        AccessTokenVo accessTokenVo = JSON.parseObject(s, AccessTokenVo.class);
        return accessTokenVo;
    }

    @Override
    public AccessTokenVo refresh(RefreshTokenDto refreshTokenDto) {

        //根据用户名查询用户信息。
        Wrapper<Login> wrapper = new EntityWrapper<>();
        wrapper.eq("username",refreshTokenDto.getUsername());
        Login login = selectOne(wrapper);

        //token,client_id,secret
        refreshUrl = String.format(refreshUrl,refreshTokenDto.getRefreshToken(),login.getClientId(),login.getClientSecret());
        String s = HttpUtil.sendPost(refreshUrl,  null);
        AccessTokenVo accessTokenVo = JSON.parseObject(s, AccessTokenVo.class);
        return accessTokenVo;
    }
}
