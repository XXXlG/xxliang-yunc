package com.xxliang.yunc.doc;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 课程文档
 * @author xxliang
 * @since 2025-06-03
 */
//(索引名称,索引类型)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "course", type = "_doc")
public class CourseDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    //文档ID类型

    @Id
    @Field(type= FieldType.Keyword)
    private Long id;

    /**
     * 课程名称
     */
    //如果type指定的是text则要分词并指定分词器，如果是keyword则不进行分词。
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;

    // 新增资源字段
    @Field(type = FieldType.Keyword) // 假设资源为字符串类型且不需要分词
    private String forUser;

    //课程类型ID
    @Field(type = FieldType.Long)
    private Long courseTypeId;

    @Field(type = FieldType.Keyword)
    private String gradeName;

    /**
     * 课程的开课时间
     */
    @Field(type = FieldType.Date)
    private Date startTime;

    //课程的结束时间
    @Field(type = FieldType.Date)
    private Date endTime;

    /**
     * 封面，云存储地址
     */
    @Field(type = FieldType.Keyword)
    private String pic;


    @Field(type = FieldType.Date)
    private Date onlineTime;


    @Field(type = FieldType.Keyword)
    private String teacherNames;


    @Field(type = FieldType.Text)
    private String chapterName;

    /**
     * 课程状态，下线：0 ， 上线：1
     */
    private Integer status;

    //课程收费，1：免费，2：收费
    @Field(type = FieldType.Keyword)
    private String charge;


    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 原价
     */

    private BigDecimal priceOld;

    /**
     * 销量
     */
    @Field(type = FieldType.Integer)
    private Integer saleCount;
    /**
     * 浏览量
     */
    @Field(type = FieldType.Integer)
    private Integer viewCount;
    /**
     * 评论数
     */
    @Field(type = FieldType.Integer)
    private Integer commentCount;


}
