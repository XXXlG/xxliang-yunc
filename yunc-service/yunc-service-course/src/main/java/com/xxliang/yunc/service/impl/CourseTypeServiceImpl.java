package com.xxliang.yunc.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xxliang.yunc.constant.ValidateConstant;
import com.xxliang.yunc.domain.CourseType;
import com.xxliang.yunc.exception.GlobalBussnessException;
import com.xxliang.yunc.mapper.CourseTypeMapper;
import com.xxliang.yunc.service.ICourseTypeService;
import com.xxliang.yunc.vo.CourseTypeCrumbsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.*;

import static java.lang.Thread.sleep;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.c;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-06-03
 */
@Service
@Slf4j
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    RedisTemplate redisTemplate;

    String courseTypeKey = ValidateConstant.COURSE_TYPE_KEY;
    @Autowired
    private CourseTypeMapper courseTypeMapper;


    @Override
    public List<CourseTypeCrumbsVo> crumbs(Long id) {
        //JR303校验
        CourseType courseType = courseTypeMapper.selectById(id);
        if(ObjectUtils.isEmpty(courseType)){
            throw new GlobalBussnessException("课程目录不存在");
        }
        //面包屑查询。可以优化一下for循环数据...
        String[] ids = courseType.getPath().split("\\.");
        List<CourseType> courseTypes = selectBatchIds(Arrays.asList(ids));
        List<CourseTypeCrumbsVo> CourseTypeCrumbsVos = new ArrayList<>();

        courseTypes.forEach(type -> {
            EntityWrapper<CourseType> wrapper = new EntityWrapper<>();
            wrapper.eq("pid", type.getPid());
            wrapper.notIn("id", type.getId());
            List<CourseType> others = selectList(wrapper);
            CourseTypeCrumbsVos.add(new CourseTypeCrumbsVo(type, others));
        });

        return CourseTypeCrumbsVos;
    }

    /**
     * k: course_type::treeData
     * v: List<CourseType>
     * @return
     */

    @Override
    @Cacheable(cacheNames = "course_type",  key = "'treeData'")
    public List<CourseType> treeData() {
        return getCourseTypes();
    }


    public List<CourseType> getCourseTypes() {
        //获取全部的课程类型
        List<CourseType> allCourseType = selectList(null);
        HashMap<Long, CourseType> map = new HashMap<>();
        for (CourseType courseType : allCourseType) {
            map.put(courseType.getId(), courseType);
        }
        List<CourseType> firstCourseType = new ArrayList<>();
        for (CourseType courseType : allCourseType) {
            if (courseType.getPid() == 0) {
                firstCourseType.add(courseType);
            } else {
                CourseType parent = map.get(courseType.getPid());
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(courseType);
            }
        }
        return firstCourseType;
    }
    @CacheEvict(cacheNames = "course_type",  key = "'treeData'")
    @Override
    public boolean insert(CourseType courseType) {
        boolean b = super.insert(courseType);
        return b;
    }

    @Override
    @CacheEvict(cacheNames = "course_type",  key = "'treeData'")
    public boolean updateById(CourseType courseType) {
        boolean b = super.updateById(courseType);
        if (b) {
            redisTemplate.delete(courseTypeKey);
        }
        return b;
    }
    @CacheEvict(cacheNames = "course_type",  key = "'treeData'")
    @Override
    public boolean deleteById(Serializable serializable) {
        boolean b = super.deleteById(serializable);
        if (b) {
            redisTemplate.delete(courseTypeKey);
        }
        return b;
    }

}
