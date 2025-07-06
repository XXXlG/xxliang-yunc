package com.xxliang.yunc.service.impl;

import com.alibaba.fastjson.JSON;
import com.xxliang.yunc.domain.Login;
import com.xxliang.yunc.domain.MediaFile;
import com.xxliang.yunc.service.ServicePro;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author xxliang
 * @date 2025/6/7  15:57
 * @description
 */
@Service
@Slf4j
public class ServiceProImpl implements ServicePro{

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public boolean sendMessage(MediaFile message) {
        SendResult sendResult = rocketMQTemplate.syncSend("media-file:sms", MessageBuilder.withPayload(message).build());
        System.out.println(sendResult);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }



}
