package com.xxliang.yunc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author xxliang
 * @date 2025/6/3  14:03
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    private Integer type;
}
