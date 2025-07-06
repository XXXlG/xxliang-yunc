package com.xxliang.yunc.config;

import com.xxliang.yunc.domain.Permission;
import com.xxliang.yunc.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;


/**
 * @author xxliang
 * @date 2025/6/1  21:54
 * @description WebSecurity配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true) //开启全局方法权限控制,在方法调用前做权限校验。
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IPermissionService permissionService;

    public static void main(String[] args) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String encode = bc.encode("123");
        System.out.println(encode);
        System.out.println(bc.matches("123", encode));

    }
    //密码编码器-负责加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 要自己 加盐加密
        return new BCryptPasswordEncoder();
    }
//   认证管理器
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    //授权规则配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        List<Permission> permissions = permissionService.selectList(null);
        for ( Permission permission : permissions){
            http.authorizeRequests().antMatchers(permission.getResource()).hasAuthority(permission.getSn());
        }

        http.authorizeRequests()                                //授权配置
                .antMatchers("/login","/login/common","/login/refresh").permitAll()  //登录路径放行
                .anyRequest().authenticated()                   //其他路径都要认证之后才能访问
                .and().formLogin()                              //允许表单登录
                .successForwardUrl("/login/success")             // 设置登陆成功页
                .and().logout().permitAll()                    //登出(退出登录)路径放行 /logout
                .and().csrf().disable();                        //关闭跨域伪造检查,默认情况开启。
    }

}