package com.xxliang.yunc.service.impl;

import com.xxliang.yunc.doc.CourseDoc;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.pojo.SearchDto;
import com.xxliang.yunc.repository.ESCRUDRepository;
import com.xxliang.yunc.result.PageList;
import com.xxliang.yunc.service.ESCourseService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author xxliang
 * @date 2025/6/14  14:49
 * @description
 */
@Service
public class ESCourseServiceImpl implements ESCourseService {


    @Autowired
    private ESCRUDRepository eSCRUDRepository;

    @Override
    public void save(CourseDoc courseDoc) {
        CourseDoc save = eSCRUDRepository.save(courseDoc);
        if (ObjectUtils.isEmpty(save)) {
            throw new GlobalBussnessException("保存失败");
        }
    }

    @Override
    public PageList<CourseDoc> search(SearchDto searchDto) {
        //TODO 搜索
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        String fieldName = "jg";
        if (StringUtils.isNoneEmpty(searchDto.getSortField())) {
            switch (searchDto.getSortField().toLowerCase()) {
                case "xp":
                    fieldName = "onlineTime";
                    break;
                case "pl":
                    fieldName = "commentCount";
                    break;
                case "rq":
                    fieldName = "viewCount";
                    break;
                case "xl":
                    fieldName = "saleCount";
                    break;
            }
        }

        SortOrder sortOrder = SortOrder.DESC;

        if (StringUtils.isNotEmpty(searchDto.getSortType()) &&
                searchDto.getSortType().equalsIgnoreCase("asc")) {
            sortOrder = SortOrder.ASC;
        }

        // 指定查询条件
        builder.withSort(new FieldSortBuilder(fieldName).order(sortOrder));


        builder.withPageable(PageRequest.of(searchDto.getPage() - 1, searchDto.getRows()));
/*
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(StringUtils.isNotEmpty(searchDto.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("name",searchDto.getKeyword()));
        }
 //分类，等级，价格等。。
        if(searchDto.getCourseTypeId()!=null){
            boolQueryBuilder.filter(QueryBuilders.termQuery("courseTypeId",
                    searchDto.getCourseTypeId()));
        }

        if(StringUtils.isNotEmpty(searchDto.getGradeName())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("gradeName",
                    searchDto.getGradeName()));
        }

        if(null != searchDto.getPriceMin()){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").
                    gte(searchDto.getPriceMin()));
        }

        if(null != searchDto.getPriceMax()){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").
                    gte(searchDto.getPriceMax()));
        }

        builder.withQuery(boolQueryBuilder);
*/
        //This is es page_query for search
        Page<CourseDoc> page = eSCRUDRepository.search(builder.build());


        //return data for service.
        return new PageList(page.getTotalElements(), page.getContent());

    }
}
