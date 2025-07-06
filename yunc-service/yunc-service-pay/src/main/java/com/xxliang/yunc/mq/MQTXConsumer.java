package com.xxliang.yunc.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.domain.PayOrder;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.service.IPayOrderService;
import com.xxliang.yunc.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author xxliang
 * @date 2025/6/15  18:30
 * @description 消费订单发送的事务消息
 */
@Slf4j
@RocketMQMessageListener(
        //  主题
        topic = ValidateConstant.MQ_TOPIC_ORDER,
        //标签
        selectorExpression = ValidateConstant.MQ_TAGS_COURSEORDER_PAYORDER,
        //分组
        consumerGroup = "service-common-consumer-order")
@Component
public class MQTXConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private IPayOrderService payOrderService;

    /**
     * 真正要消费的接口
     *
     * @param messageExt
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        /**
         * 如果消费失败了，要存储消费消息进行人工补偿。
         */
        AssertUtil.isNull(messageExt, "消息不能为空");
        try {
            log.info("订单正在保存！");
            if (messageExt != null && messageExt.getBody() != null && messageExt.getBody().length > 0) {
                byte[] body = messageExt.getBody();
                String s = new String(body, StandardCharsets.UTF_8);
                PayOrder payOrder = JSON.parseObject(s, PayOrder.class);
                AssertUtil.isNull(payOrder, "订单不能为空");
                PayOrder payOrder1 = payOrderService.selectOne(new EntityWrapper<PayOrder>().eq("order_no", payOrder.getOrderNo()));
                AssertUtil.isNotNull(payOrder1, "订单已存在,请勿重复插入");
                payOrder.setPayStatus(0);
                payOrderService.insert(payOrder);
            }
            log.info("订单保存完成");
        } catch (Exception e) {
            throw new GlobalBussnessException("订单保存失败，联系管理员");
        }
    }
}
