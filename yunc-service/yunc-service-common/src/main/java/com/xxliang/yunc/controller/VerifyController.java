package com.xxliang.yunc.controller;

import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.service.VerifyService;
import com.xxliang.yunc.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

/**
 * @author xxliang
 * @date 2025/5/30  23:21
 * @description 验证相关接口
 */
@Validated
@RestController
@RequestMapping("/verifycode")
public class VerifyController {
    @Autowired
    private VerifyService verifyService;

    /**
     * 发送短信服务验证码
     */
    @GetMapping("/sendSmsCode/{mobile}")
    public JSONResult sendSmsCode(@PathVariable("mobile")
                                  @Pattern(regexp = ValidateConstant.TEL_REGEX, message = "手机号格式不正确")
                                  String mobile) {
        //调用service层发生短信服务
        AssertUtil.isZero(verifyService.sendSmsCode(mobile), "验证码发生失败请联系管理员！");
        return JSONResult.success();
    }
}
