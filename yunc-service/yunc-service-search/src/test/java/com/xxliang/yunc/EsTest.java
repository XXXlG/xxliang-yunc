package com.xxliang.yunc;

import com.xxliang.yunc.doc.CourseDoc;
import com.xxliang.yunc.repository.ESCRUDRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author xxliang
 * @date 2025/6/8  10:41
 * @description
 */
@SpringBootTest(classes = SearchApp.class)
@RunWith(SpringRunner.class)
public class EsTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;


    @Autowired
    private ESCRUDRepository esRepository;



    @Test
    public void test1() {
        //创建索引
        elasticsearchTemplate.createIndex(CourseDoc.class);
        //文档映射
        elasticsearchTemplate.putMapping(CourseDoc.class);

    }

    @Test
    public void test4() {
        Optional<CourseDoc> byId = esRepository.findById(1L);
        System.out.println(byId.get());
    }

    @Test
    public void test41() {
        esRepository.deleteById(1L);
    }
    @Test
    public void test3() {
        CourseDoc java = CourseDoc.builder().id(1L).name("java").courseTypeId(1L).build();
        esRepository.save(java);
    }


    @Test
    public void test2() {
        ArrayList<CourseDoc> courseDocs = new ArrayList<>(20);
        for (long i = 0; i < 10; i++) {
            courseDocs.add(CourseDoc.builder()
                    .id(i)
                    .name("测试课程" + i)
                    .courseTypeId(i)
                    .build());
        }
        esRepository.saveAll(courseDocs);
    }
}
