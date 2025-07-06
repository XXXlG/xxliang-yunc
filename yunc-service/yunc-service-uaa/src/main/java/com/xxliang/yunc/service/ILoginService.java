package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.Login;
import com.baomidou.mybatisplus.service.IService;
import com.xxliang.yunc.dto.LoginDto;
import com.xxliang.yunc.dto.RefreshTokenDto;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.vo.AccessTokenVo;

import javax.validation.Valid;

/**
 * <p>
 * 登录表 服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
public interface ILoginService extends IService<Login> {

    AccessTokenVo common(LoginDto loginDtp);

    AccessTokenVo refresh(RefreshTokenDto refreshTokenDto);
}
