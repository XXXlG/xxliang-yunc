package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.User;
import com.baomidou.mybatisplus.service.IService;
import com.xxliang.yunc.dto.PhoneRegisterDto;

/**
 * <p>
 * 会员登录账号 服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
public interface IUserService extends IService<User> {

    Integer register(PhoneRegisterDto phoneRegisterDto);
}
