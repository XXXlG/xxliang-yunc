package com.xxliang.yunc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author xxliang
 * @date 2025/6/3  17:20
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenDto {
    @NotNull(message = "refreshToken不能为空")
    private String refreshToken;
    @NotNull(message = "username不能为空")
    private String username;
}
