package com.xxliang.yunc.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xxliang.yunc.api.LoginApi;
import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.domain.Login;
import com.xxliang.yunc.domain.User;
import com.xxliang.yunc.domain.UserAccount;
import com.xxliang.yunc.domain.UserBaseInfo;
import com.xxliang.yunc.dto.PhoneRegisterDto;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.mapper.UserMapper;
import com.xxliang.yunc.pojo.VerifyCode;
import com.xxliang.yunc.service.IUserAccountService;
import com.xxliang.yunc.service.IUserBaseInfoService;
import com.xxliang.yunc.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xxliang.yunc.util.AssertUtil;
import com.xxliang.yunc.util.StrUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * <p>
 * 会员登录账号 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IUserAccountService userAccountService;
    @Autowired
    IUserBaseInfoService userBaseInfoService;
    @Autowired
    LoginApi loginApi;

    /**
     * 手机注册
     * @param phoneRegisterDto
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)//全局事务ID;
    public Integer register(PhoneRegisterDto phoneRegisterDto) {
        //After JR303校验

        String mobile = phoneRegisterDto.getMobile(); //手机号
        String password = phoneRegisterDto.getPassword(); // 密码
        String smsCode = phoneRegisterDto.getSmsCode();//短信验证码
        Integer regChannel = phoneRegisterDto.getRegChannel();//注册渠道

        //校验手机号是否已经被注册？
        Wrapper<User> wrapper = new EntityWrapper<>();
        //参数一：字段名,参数二：值
        wrapper.eq("phone",mobile);
        User user = selectOne(wrapper);
        AssertUtil.isNotNull(user,"手机号已经被注册");
        //MP测试
        User user1 = userMapper.selectOne(User.builder().phone(mobile).build());
        log.info("user1:{}",user1);
        //获取验证码
        String key = String.format(ValidateConstant.REDIS_KEY_SMS, mobile);
        VerifyCode verifyCode = (VerifyCode) redisTemplate.opsForValue().get(key);
        //验证码是否过期？
        AssertUtil.isNull(verifyCode,"验证码已过期,请重新获取~");

        //验证码是否正确?
        if(!verifyCode.getCode().equals(smsCode)){throw new GlobalBussnessException("验证码错误");}

        //以上之后开始备份；
        //loginApi.phoneRegister(User.builder().username(mobile).password(password).type(1).build());

        //密码加密
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        //首先备份login表,并获取login_id
        Login login = Login.builder()
                .username(mobile)
                .password(bc.encode(password))//加密
                .type(Login.LOGIN_TYPE_USER)
                .build();
        Long loginId = Long.valueOf(loginApi.phoneRegister(login).getData().toString()) ;
        //
        user = User.builder()
                .phone(mobile)
                .loginId(loginId)
                .nickName(mobile)
                .createTime(new Date().getTime())
                .build();
        //插入
        insert(user);

        //创建账户 => t_user_account
        UserAccount userAccount = UserAccount.builder()
                .id(user.getId())
                .createTime(new Date().getTime())
                .password(password)
                .build();
        userAccountService.insert(userAccount);

        //再插入基础信息biao
        UserBaseInfo userBaseInfo = UserBaseInfo.builder()
                .id(user.getId())
                .createTime(new Date().getTime())
                .regChannel(regChannel)
                .build();
        userBaseInfoService.insert(userBaseInfo);

        //验证码等待3min过期
        return 1;
    }
}
