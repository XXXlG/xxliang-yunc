package com.xxliang.yunc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/15  13:55
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageStationDto {
    private String title;
    private String content;
    private String type;
    // 多个用户发送
    private List<Long> ids;
}
