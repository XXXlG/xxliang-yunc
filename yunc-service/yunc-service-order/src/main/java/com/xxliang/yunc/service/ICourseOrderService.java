package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.CourseOrder;
import com.baomidou.mybatisplus.service.IService;
import com.xxliang.yunc.dto.PlaceOrderDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-21
 */
public interface ICourseOrderService extends IService<CourseOrder> {

    String placeOrder(PlaceOrderDto placeOrderDto);

    void saveOrderAndItem(CourseOrder courseOrder);
}
