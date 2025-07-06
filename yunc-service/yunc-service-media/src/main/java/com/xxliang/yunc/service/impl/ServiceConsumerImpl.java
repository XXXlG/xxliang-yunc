package com.xxliang.yunc.service.impl;

import com.alibaba.fastjson.JSON;
import com.xxliang.yunc.domain.MediaFile;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.service.IMediaFileService;
import com.xxliang.yunc.service.ServiceConsumer;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xxliang
 * @date 2025/6/7  22:23
 * @description
 */

@Slf4j
@Component
@RocketMQMessageListener(topic = "message",//topic
        selectorExpression="sms"	//tags
        ,consumerGroup = "service-consumer")
public class ServiceConsumerImpl implements RocketMQListener<MessageExt> {
    @Autowired
    IMediaFileService mediaFileService;
    //推流逻辑
    @Override
    @Transactional
    public void onMessage(MessageExt message) {
        try {
            String msg = new String(message.getBody(), CharsetUtil.UTF_8);
            MediaFile mediaFile = JSON.parseObject(msg, MediaFile.class);
            log.info("消费者 {} ",msg);
            mediaFileService.handleFile2m3u8(mediaFile);
        }catch (Exception e){
            throw new GlobalBussnessException("消费失败，联系管理员");
        }
    }
}
