package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.CourseTeacher;
import com.baomidou.mybatisplus.service.IService;
import com.xxliang.yunc.domain.Teacher;

import java.util.List;

/**
 * <p>
 * 课程和老师的中间表 服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-03
 */
public interface ICourseTeacherService extends IService<CourseTeacher> {

    List<Teacher> queryTeachersByCourseId(Long id);
}
