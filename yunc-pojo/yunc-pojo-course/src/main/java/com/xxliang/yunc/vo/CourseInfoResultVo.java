package com.xxliang.yunc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xxliang
 * @date 2025/6/21  17:05
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoResultVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CourseInfoVo> courseInfoVoList;
    private BigDecimal totalAmount;
}
