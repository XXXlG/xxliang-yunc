package com.xxliang.yunc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/15  14:55
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSMSDto {
    private List<User2PhoneDto> user2Phone;
    private String content;
    private String title;
    private String type;
    private String ip;
}
