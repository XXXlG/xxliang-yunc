package com.xxliang.yunc.service.impl;

import com.xxliang.yunc.domain.UserAccount;
import com.xxliang.yunc.mapper.UserAccountMapper;
import com.xxliang.yunc.service.IUserAccountService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

}
