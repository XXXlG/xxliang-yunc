package com.xxliang.yunc.constant;

/**
 * @author xxliang
 * @date 2025/5/30  15:06
 * @description 校验规则相关
 */
public interface ValidateConstant{
    String TEL_REGEX = "^[1](([3][0-9])|([4][0-9])|([5][0-9])|([6][0-9])|([7][0-9])|([8][0-9])|([9][0-9]))[0-9]{8}$";

    // <editor-fold desc="注册短信redis相关">===================================================

    String REDIS_KEY_SMS = "sms:%s";
    //3min之后才可以重发
    long REDIS_KEY_SMS_TIMEOUT = 60 * 1000 * 3;
    //1min之内发生上一次验证码
    long REDIS_KEY_SMS_RETRY_TIMEOUT = 60 * 1000;
    //验证码长度
    int REDIS_KEY_SMS_CODE_LENGTH = 4;
    // </editor-fold>===================================================================


    // <editor-fold desc="课程目录的redis">===================================================
    String COURSE_TYPE_KEY = "course_type::treeData";
    // </editor-fold>===================================================================


    // <editor-fold desc="推送消息相关">===================================================

    /**
     * 推送消息的topic
     */
    String ROCKETMQ_TOPIC_PUBLISH = "topic-publish";
    /**
     * 站内推送
     */
    String ROCKETMQ_TAGS_STATION = "tags-station";
    /**
     * 邮件推送
     */
    String ROCKETMQ_TAGS_EMAIL = "topic-email";
    /**
     * 短信推送
     */
    String ROCKETMQ_TAGS_SMS = "topic-sms";

    // </editor-fold>===================================================================


    // <editor-fold desc="防止订单重复">===================================================

    String REDIS_KEY_ORDER_TOKEN_KEY = "order:token";
    // </editor-fold>===================================================================

    // <editor-fold desc="">===================================================

    //事务消息组
    String MQ_COURSEORDER_PAY_GROUP_TRANSACTION = "mq_courseorder_pay_group_transaction";
    // 事务消息topic:tags
    String MQ_TOPIC_ORDER = "mq_topic_order";
    String MQ_TAGS_COURSEORDER_PAYORDER = "mq_tags_courseorder_payorder";

    // </editor-fold>===================================================================
}
