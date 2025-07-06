package com.xxliang.yunc.feignFallBack;

import com.xxliang.yunc.api.ESCourseFeign;
import com.xxliang.yunc.doc.CourseDoc;
import com.xxliang.yunc.result.JSONResult;
import feign.hystrix.FallbackFactory;

/**
 * @author xxliang
 * @date 2025/5/31  19:03
 * @description 降级工厂
 */
public class ESCourseFeignFallback implements FallbackFactory<ESCourseFeign> {
    @Override
    public ESCourseFeign create(Throwable throwable) {
        return new ESCourseFeign() {
            @Override
            public JSONResult save(CourseDoc courseDoc) {
                return JSONResult.error("ESCourseFeign.save调用错误。");
            }
        };
    }
}
