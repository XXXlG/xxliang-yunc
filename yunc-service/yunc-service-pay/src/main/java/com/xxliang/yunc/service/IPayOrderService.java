package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.AlipayNotifyDto;
import com.xxliang.yunc.domain.PayApplyDto;
import com.xxliang.yunc.domain.PayOrder;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-22
 */
public interface IPayOrderService extends IService<PayOrder> {

    void payNotify(AlipayNotifyDto alipayNotifyDto);

    void checkPayOrder(String orderId);

    String apply(PayApplyDto payApplyDto);
}
