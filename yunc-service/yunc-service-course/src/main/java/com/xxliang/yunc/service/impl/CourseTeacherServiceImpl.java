package com.xxliang.yunc.service.impl;

import com.xxliang.yunc.domain.CourseTeacher;
import com.xxliang.yunc.domain.Teacher;
import com.xxliang.yunc.mapper.CourseTeacherMapper;
import com.xxliang.yunc.service.ICourseTeacherService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 课程和老师的中间表 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-03
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements ICourseTeacherService {

    @Override
    public List<Teacher> queryTeachersByCourseId(Long id) {
        //query
        List<Teacher> teachers = baseMapper.queryTeachersByCourseId(id);
        return Collections.emptyList();
    }
}
