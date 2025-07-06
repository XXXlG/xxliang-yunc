package com.xxliang.yunc.controller;

import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxliang
 * @date 2025/6/21  16:13
 * @description
 */
@RestController
@Slf4j
public class TokenController {

    @Autowired
    private ITokenService tokenService;

    @GetMapping("/createToken/{id}")
    public JSONResult createToken(@PathVariable Long id) {
        log.info("创建令牌");
        String token = tokenService.createToken(id);
        return JSONResult.success(token);
    }

}
