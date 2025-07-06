package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.CourseType;
import com.baomidou.mybatisplus.service.IService;
import com.xxliang.yunc.vo.CourseTypeCrumbsVo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-03
 */
public interface ICourseTypeService extends IService<CourseType> {
    List<CourseType> treeData();

    List<CourseTypeCrumbsVo> crumbs(Long courseTypeId);
}
