package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.CourseChapter;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 课程章节 ， 一个课程，多个章节，一个章节，多个视频 服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-03
 */
public interface ICourseChapterService extends IService<CourseChapter> {

    List<CourseChapter> listByCourseId(Long id);
}
