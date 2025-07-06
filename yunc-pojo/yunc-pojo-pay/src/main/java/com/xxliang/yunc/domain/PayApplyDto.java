package com.xxliang.yunc.domain;

import lombok.Data;

/**
 * @author xxliang
 * @date 2025/6/27  13:36
 * @description
 */
@Data
public class PayApplyDto {
    private String callUrl;
    private String orderNo;
    private Integer payType;
}
