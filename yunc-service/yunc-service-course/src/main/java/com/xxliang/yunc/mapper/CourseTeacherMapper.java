package com.xxliang.yunc.mapper;

import com.xxliang.yunc.domain.CourseTeacher;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxliang.yunc.domain.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程和老师的中间表 Mapper 接口
 * </p>
 *
 * @author xxliang
 * @since 2025-06-03
 */
public interface CourseTeacherMapper extends BaseMapper<CourseTeacher> {

    List<Teacher> queryTeachersByCourseId(@Param("id") Long id);
}
