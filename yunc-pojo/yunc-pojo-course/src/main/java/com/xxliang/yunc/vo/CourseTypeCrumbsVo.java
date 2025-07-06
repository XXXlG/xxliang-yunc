package com.xxliang.yunc.vo;

import com.xxliang.yunc.domain.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/20  18:11
 * @description 面包屑查询
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseTypeCrumbsVo {
    private CourseType ownerProductType;
    private List<CourseType> otherProductTypes;
}
