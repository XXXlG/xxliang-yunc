package com.xxliang.yunc.service;

import io.netty.util.CharsetUtil;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xxliang
 * @date 2025/6/7  22:23
 * @description
 */
public interface ServiceConsumer {
    boolean ConsumerMessage(MessageExt message);
}
