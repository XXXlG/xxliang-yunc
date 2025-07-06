package com.xxliang.yunc.mq;

import com.alibaba.fastjson.JSONObject;
import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.domain.MessageStation;
import com.xxliang.yunc.dto.MessageStationDto;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.service.IMessageSmsService;
import com.xxliang.yunc.service.IMessageStationService;
import com.xxliang.yunc.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author xxliang
 * @date 2025/6/15  18:30
 * @description 站内推送服务监听
 */
@Slf4j
@RocketMQMessageListener(
        //  主题
        topic = ValidateConstant.ROCKETMQ_TOPIC_PUBLISH,
        //标签
        selectorExpression = ValidateConstant.ROCKETMQ_TAGS_STATION,
        //分组
        consumerGroup = "service-common-consumer-station")
@Component
public class MQCommonStationService implements RocketMQListener<MessageExt>{

    @Autowired
    IMessageStationService messageStationService;
    /**
     * 真正要消费的接口
     * @param messageExt
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        AssertUtil.isNull(messageExt,"消息不能为空");
        try {
            log.info("站内消息正在推送哦！");
            String msg = new String(messageExt.getBody(), StandardCharsets.UTF_8);
            MessageStationDto messageStationDto = JSONObject.parseObject(msg, MessageStationDto.class);
            //发送消息业务。

            for(Long userId:messageStationDto.getIds()){
                MessageStation messageStation = new MessageStation();
                BeanUtils.copyProperties(messageStationDto,messageStation);
                messageStation.setUserId(userId);
                messageStation.setSendTime(new Date());
                messageStation.setIsread(0);//默认未读
                messageStationService.insert(messageStation);//插入数据库
            }
            log.info("站消息推送成功！");
        }catch (Exception e){
            throw new GlobalBussnessException("站内推送消费失败，联系管理员");
        }
    }
}
