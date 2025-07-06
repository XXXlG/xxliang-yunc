package com.xxliang.yunc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxliang
 * @date 2025/6/15  14:57
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User2PhoneDto {
    private String phone;
    private Long userId;
}
