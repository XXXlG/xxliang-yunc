package com.xxliang.yunc.controller;

import com.xxliang.yunc.doc.CourseDoc;
import com.xxliang.yunc.pojo.SearchDto;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.result.PageList;
import com.xxliang.yunc.service.ESCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/14  14:34
 * @description
 */
@RestController
@RequestMapping
@Slf4j
public class ESCourseController {

    @Autowired
    private ESCourseService courseDocService;

    @PostMapping("/esCourseSave/save")
    public JSONResult save(@RequestBody CourseDoc courseDoc) {
        log.info("远程调用ES添加课程");
        courseDocService.save(courseDoc);
        return JSONResult.success();
    }

    /**
     * resource/course/search
     * @param searchDto
     * @return
     */
    @PostMapping("/course/search")
    public JSONResult search(@RequestBody SearchDto searchDto) {
        log.info("远程调用ES查询课程");
        PageList<CourseDoc> list = courseDocService.search(searchDto);
        return JSONResult.success(list);
    }
}
