package com.xxliang.yunc.service.impl;

import com.xxliang.yunc.domain.LoginLog;
import com.xxliang.yunc.mapper.LoginLogMapper;
import com.xxliang.yunc.service.ILoginLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}
