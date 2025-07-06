package com.xxliang.yunc.api;

import com.xxliang.yunc.doc.CourseDoc;
import com.xxliang.yunc.result.JSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xxliang
 * @date 2025/5/31  18:23
 * @description login的调用api
 */

@FeignClient (name = "service-search", path = "/esCourseSave")
public interface ESCourseFeign {

    @RequestMapping(value="/save",method= RequestMethod.POST)
    JSONResult save(@RequestBody CourseDoc courseDoc);

}
