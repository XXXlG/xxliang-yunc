package com.xxliang.yunc.vo;

import com.xxliang.yunc.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/21  14:30
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailVo {
    private Course course;
    private CourseMarket courseMarket;
    private List<CourseChapter> courseChapter;
    private List<Teacher> teachers;
    private CourseDetail courseDetail;
    private CourseSummary courseSummary;

}
