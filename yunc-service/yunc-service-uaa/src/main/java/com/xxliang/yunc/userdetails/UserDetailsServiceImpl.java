package com.xxliang.yunc.userdetails;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xxliang.yunc.domain.Login;
import com.xxliang.yunc.domain.Permission;
import com.xxliang.yunc.service.ILoginService;
import com.xxliang.yunc.service.IPermissionService;
import com.xxliang.yunc.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xxliang
 * @date 2025/6/1  22:42
 * @description 自己的账号查询服务
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在此查询用户名。让其自己对比用户名
        // 根据用户名查询登录信息
        Wrapper<Login> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        Login login = loginService.selectOne(wrapper);
        AssertUtil.isNull(login, "用户不存在");


        //标识权限信息
        List<? extends GrantedAuthority> authorities = null;

        //获取权限信息
        List<Permission> permissByLoginId = permissionService.getPermissByLoginId(login.getId());
        //将权限信息（sn）封装到权限集合中
        authorities = permissByLoginId.stream().map(x -> new SimpleGrantedAuthority(x.getSn())).collect(Collectors.toList());

        Login login_context = Login.builder().username(login.getUsername()).id(login.getId()).avatar(login.getAvatar()).build();

        String jsonString = JSON.toJSONString(login_context);
        //封装用户信息 = 和Login表对应。
        return User.withUsername(jsonString)
                .password(login.getPassword())
                .disabled(!login.getEnabled()) //标识账号是否可用
                .credentialsExpired(login.getCredentialsNonExpired())//标识凭据是否过期
                .accountExpired(login.getAccountNonExpired())//标识账号是否过期
                .accountLocked(login.getAccountNonLocked())//标识账号是否锁定
                .authorities(authorities)
                .build();
    }
}
