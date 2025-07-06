package com.xxliang.yunc.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author xxliang
 * @date 2025/6/3  14:54
 * @description Token接收
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessTokenVo {
    @NotNull(message = "access_token不能为空")
    String access_token;
    @NotNull(message = "token_type不能为空")
    String token_type;
    @NotNull(message = "refresh_token不能为空")
    String refresh_token;
    @NotNull(message = "expires_in不能为空")
    Long expires_in;
    @NotNull(message = "scope不能为空")
    String scope;
    @NotNull(message = "jti不能为空")
    String jti;
}
