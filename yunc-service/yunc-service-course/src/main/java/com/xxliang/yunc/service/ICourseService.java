package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.Course;
import com.baomidou.mybatisplus.service.IService;
import com.xxliang.yunc.domain.CourseType;
import com.xxliang.yunc.vo.CourseDetailVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-03
 */
public interface ICourseService extends IService<Course> {


    Course onLineCourse(Long id);

    CourseDetailVo detail(Long id);
}
