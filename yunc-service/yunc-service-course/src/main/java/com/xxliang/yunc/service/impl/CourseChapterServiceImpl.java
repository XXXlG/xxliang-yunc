package com.xxliang.yunc.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xxliang.yunc.domain.CourseChapter;
import com.xxliang.yunc.mapper.CourseChapterMapper;
import com.xxliang.yunc.service.ICourseChapterService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程章节 ， 一个课程，多个章节，一个章节，多个视频 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-03
 */
@Service
public class CourseChapterServiceImpl extends ServiceImpl<CourseChapterMapper, CourseChapter> implements ICourseChapterService {

    @Override
    public List<CourseChapter> listByCourseId(Long id) {

        List<CourseChapter> list = selectList(new EntityWrapper<CourseChapter>().eq("course_id",id));

        return list;
    }
}
