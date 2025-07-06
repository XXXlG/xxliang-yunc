package com.xxliang.yunc.api;


import com.xxliang.yunc.domain.CourseOrder;
import com.xxliang.yunc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xxliang
 * @date 2025/5/31  18:23
 * @description login的调用api
 */

@FeignClient(name = "service-order", path = "/courseOrder")
public interface OrderApi {
    @GetMapping(value="/UpdateById/{orderNo}")
    public JSONResult UpdateById(@PathVariable("orderNo")String  orderNo);
}
