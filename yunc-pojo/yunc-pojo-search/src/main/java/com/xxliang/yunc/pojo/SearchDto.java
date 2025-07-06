package com.xxliang.yunc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxliang
 * @date 2025/6/20  20:07
 * @description 搜索数据模型接收
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private String chargeName;
    private String courseTypeId; // 根据实际需要可改为Integer
    private String gradeName;
    private String keyword;
    private Integer page;
    private Integer priceMax;
    private Integer priceMin;
    private Integer rows;
    private String sortField ;
    private String sortType = "desc";
}
