package com.xxliang.yunc.api;

import com.xxliang.yunc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author xxliang
 * @date 2025/5/31  18:23
 * @description login的调用api
 */

@FeignClient(name = "service-course", path = "/course")
public interface CourseApi {
    @GetMapping("/info/{courseId}")
    public JSONResult courseInfo(@PathVariable("courseId") String courseId);
}
