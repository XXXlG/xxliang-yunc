package com.xxliang.yunc.service;

import com.xxliang.yunc.doc.CourseDoc;
import com.xxliang.yunc.pojo.SearchDto;
import com.xxliang.yunc.result.PageList;

import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/14  14:49
 * @description
 */
public interface ESCourseService {

    void save(CourseDoc courseDoc);

    PageList<CourseDoc> search(SearchDto searchDto);
}
