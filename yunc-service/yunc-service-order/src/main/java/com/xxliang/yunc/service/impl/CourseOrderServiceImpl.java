package com.xxliang.yunc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xxliang.yunc.api.CourseApi;
import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.domain.*;
import com.xxliang.yunc.dto.PlaceOrderDto;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.mapper.CourseOrderMapper;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.service.ICourseOrderItemService;
import com.xxliang.yunc.service.ICourseOrderService;
import com.xxliang.yunc.util.CodeGenerateUtils;
import com.xxliang.yunc.utils.LoginContext;
import com.xxliang.yunc.vo.CourseInfoResultVo;
import com.xxliang.yunc.vo.CourseInfoVo;
import io.netty.util.internal.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-21
 */
@Slf4j
@Service
public class CourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder> implements ICourseOrderService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CourseApi courseApi;
    @Autowired
    ICourseOrderItemService courseOrderItemService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    /**
     * Here to get orderNumber
     *
     * @param placeOrderDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String placeOrder(PlaceOrderDto placeOrderDto) {
        //参数校验
        if (placeOrderDto.getCourseIds().isEmpty()) {
            throw new GlobalBussnessException("订单空异常，请联系管理员");
        }

        //2. token校验
        String token = (String) redisTemplate.opsForValue().get(ValidateConstant.REDIS_KEY_ORDER_TOKEN_KEY);

        if (StringUtils.isEmpty(token) || !token.equals(placeOrderDto.getToken())) {
            throw new GlobalBussnessException("重复提交订单，请重试");
        }
        //生成订单信息‘


        List<Long> courseIds = placeOrderDto.getCourseIds();
        String courseIdStr = courseIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        JSONResult jsonResult = courseApi.courseInfo(courseIdStr);
        Object data = jsonResult.getData();

        //将data转化为CourseInfoResultVo
        CourseInfoResultVo courseInfoResultVo = JSON.parseObject(JSON.toJSONString(data), CourseInfoResultVo.class);
        //用户上下文
        Login login =new Login();
        if(ObjectUtils.isEmpty( login.getId())){
            login.setId(32l);
        }

        //总价
        BigDecimal totalAmount = courseInfoResultVo.getTotalAmount();
        //课程信息
        List<CourseInfoVo> courseInfoVoList = courseInfoResultVo.getCourseInfoVoList();
        //订单号生成
        String orderSn = CodeGenerateUtils.generateOrderSn(login.getId());
        //订单表
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setCreateTime(new Date());
        courseOrder.setUpdateTime(new Date());
        courseOrder.setOrderNo(orderSn);
        courseOrder.setTotalAmount(totalAmount);
        courseOrder.setTotalCount(courseInfoVoList.size());
        courseOrder.setStatusOrder(0);
        courseOrder.setUserId(login.getId());
        courseOrder.setTitle(courseInfoVoList.get(0).getCourse().getName());
        courseOrder.setPayType(placeOrderDto.getPayType());
        courseOrder.setVersion(0);
       // boolean insert = insert(courseOrder);

        //订单明细表

        ArrayList<CourseOrderItem> CourseOrderItems = new ArrayList<>();
        for (CourseInfoVo courseInfoVo : courseInfoVoList) {
             Course course  = courseInfoVo.getCourse();
             CourseMarket courseMarket = courseInfoVo.getCourseMarket();
             CourseOrderItem courseOrderItem = new CourseOrderItem();
             courseOrderItem.setCourseId(course.getId());
             courseOrderItem.setCourseName(course.getName());
             courseOrderItem.setCoursePic(course.getPic());
             courseOrderItem.setAmount(courseMarket.getPrice());
             courseOrderItem.setCount(1);
             courseOrderItem.setOrderNo(orderSn);
             courseOrderItem.setVersion(1);
             courseOrderItem.setCreateTime(new Date());
             courseOrderItem.setUpdateTime(new Date());
             courseOrderItem.setOrderId(courseOrder.getId());
        //     courseOrderItemService.insert(courseOrderItem);
            CourseOrderItems.add(courseOrderItem);
         }

        courseOrder.setCourseOrderItemList(CourseOrderItems);

        log.info("订单Info：{}",courseOrder);
        PayOrder payOrder = new PayOrder();
        payOrder.setCreateTime(new Date());
        payOrder.setUpdateTime(new Date());
        payOrder.setAmount(courseOrder.getTotalAmount());
        payOrder.setOrderNo(courseOrder.getOrderNo());
        payOrder.setPayType(courseOrder.getPayType());
        payOrder.setRelationId(courseOrder.getId());
        payOrder.setUserId(courseOrder.getUserId());
        payOrder.setSubject(courseOrder.getTitle());
        payOrder.setExtParams(JSON.toJSONString(courseOrder.getOrderNo()));//扩展的消息，只有本地执行事务的时候才可以使用.

        //消息内容到底应该长成什么样子？
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(
                //事务监听器组名字
                ValidateConstant.MQ_COURSEORDER_PAY_GROUP_TRANSACTION,
                //主题：标签
                ValidateConstant.MQ_TOPIC_ORDER + ":" + ValidateConstant.MQ_TAGS_COURSEORDER_PAYORDER,
                //消息：用作保存支付单
                MessageBuilder.withPayload(JSON.toJSONString(payOrder)).build(),
                //参数：用作保存课程订单和明细
                courseOrder);

        if (transactionSendResult.getSendStatus() == SendStatus.SEND_OK) {
            log.info("订单创建成功：{}", courseOrder);
        } else {
            throw new GlobalBussnessException("订单创建失败");
        }
        //清除redis中的token
        redisTemplate.delete(ValidateConstant.REDIS_KEY_ORDER_TOKEN_KEY);
        return orderSn;
    }

    //保存订单和订单明细
    @Override
    public void saveOrderAndItem(CourseOrder courseOrder) {
        //如果不能为空的话
        if(courseOrder==null){
            throw new GlobalBussnessException("课程订单不能为空");
        }
        //防止重复提交
        Wrapper<CourseOrder> orderNo = new EntityWrapper<CourseOrder>().eq("order_no", courseOrder.getOrderNo());
        if(!ObjectUtils.isEmpty(selectOne(orderNo))){
            throw new GlobalBussnessException("订单已存在");
        }

        insert(courseOrder);
        List<CourseOrderItem> courseOrderItemList = courseOrder.getCourseOrderItemList();
        courseOrderItemService.insertBatch(courseOrderItemList);

    }
}
