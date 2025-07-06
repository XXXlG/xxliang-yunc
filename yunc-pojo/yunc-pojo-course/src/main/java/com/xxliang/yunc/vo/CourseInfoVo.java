package com.xxliang.yunc.vo;

import com.xxliang.yunc.domain.Course;
import com.xxliang.yunc.domain.CourseMarket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxliang
 * @date 2025/6/21  17:04
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoVo {
    private Course course;
    private CourseMarket  courseMarket;
}
