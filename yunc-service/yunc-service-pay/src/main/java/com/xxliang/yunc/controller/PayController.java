package com.xxliang.yunc.controller;

import com.xxliang.yunc.domain.AlipayNotifyDto;
import com.xxliang.yunc.domain.PayApplyDto;
import com.xxliang.yunc.domain.PayOrder;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.service.IPayOrderService;
import com.xxliang.yunc.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xxliang
 * @date 2025/6/27  10:59
 * @description
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    IPayOrderService payOrderService;

    //支付宝异步回调
    @PostMapping("/alipay/notify")
    public void notify(AlipayNotifyDto alipayNotifyDto) {
        try{
            payOrderService.payNotify(alipayNotifyDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 检查订单是否生成？
     * @param orderId
     * @return
     */
    @GetMapping("/checkPayOrder/{orderId}")
    public JSONResult checkPayOrder(@PathVariable String orderId){
        payOrderService.checkPayOrder(orderId);
        return JSONResult.success();
    }


    /**
     * 申请支付
     * @param payApplyDto
     * @return
     */

    @PostMapping("/apply")
    public JSONResult apply(@RequestBody PayApplyDto payApplyDto){
        String form = payOrderService.apply(payApplyDto);
        return JSONResult.success(form);
    }
}
