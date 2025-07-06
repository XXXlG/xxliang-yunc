package com.xxliang.yunc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author xxliang
 * @date 2025/5/31  00:16
 * @description 验证码的pojo数据模型；
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCode {
    private String code;
    private Long timer;
}
