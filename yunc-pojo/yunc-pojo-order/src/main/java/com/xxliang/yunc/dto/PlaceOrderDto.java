package com.xxliang.yunc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/21  23:13
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDto {
    private List<Long> courseIds = new ArrayList<>();
    private Integer payType;
    private String token;
    private Integer type;
}
