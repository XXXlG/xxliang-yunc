package com.xxliang.yunc.repository;

import com.xxliang.yunc.doc.CourseDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xxliang
 * @date 2025/6/8  13:39
 * @description
 */
@Repository// 把当前的类交给spring管理
public interface ESCRUDRepository extends ElasticsearchRepository<CourseDoc, Long> {
}
