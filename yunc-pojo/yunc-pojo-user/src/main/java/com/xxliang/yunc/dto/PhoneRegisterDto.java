package com.xxliang.yunc.dto;

import com.xxliang.yunc.constant.ValidateConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author xxliang
 * @date 2025/5/31  13:14
 * @description 接受注册数据模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneRegisterDto {
    @Pattern(regexp = ValidateConstant.TEL_REGEX  ,message = "手机号码格式错误")
    String mobile;

    @Size (min = 6,max = 20,message = "密码长度在6-20位之间")
    @NotNull(message = "密码不能为空")
    String password;
//    这是注册的渠道
    Integer regChannel;
    @NotEmpty (message = "短信验证码不能为空")
    String smsCode;
}
