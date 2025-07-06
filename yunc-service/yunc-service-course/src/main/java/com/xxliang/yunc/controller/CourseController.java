package com.xxliang.yunc.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xxliang.yunc.domain.CourseMarket;
import com.xxliang.yunc.mapper.CourseMarketMapper;
import com.xxliang.yunc.query.CourseQuery;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.result.PageList;
import com.xxliang.yunc.service.ICourseMarketService;
import com.xxliang.yunc.service.ICourseService;
import com.xxliang.yunc.domain.Course;
import com.baomidou.mybatisplus.plugins.Page;
import com.xxliang.yunc.service.impl.CourseMarketServiceImpl;
import com.xxliang.yunc.vo.CourseDetailVo;
import com.xxliang.yunc.vo.CourseInfoResultVo;
import com.xxliang.yunc.vo.CourseInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    public ICourseService courseService;
    @Autowired
    private ICourseMarketService iCourseMarketService;
    @Autowired
    private CourseMarketMapper courseMarketMapper;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody Course course){
        if(course.getId()!=null){
            courseService.updateById(course);
        }else{
            courseService.insert(course);
        }
        return JSONResult.success();
    }
    //http://localhost:10010/ymcc/course/course/onLineCourse/2

    @RequestMapping(value="/detail/data/{courseId}",method= RequestMethod.GET)
    public JSONResult courseDetail(@PathVariable("courseId") Long id){
        CourseDetailVo courseDetailVo = courseService.detail(id);
        return JSONResult.success(courseDetailVo);
    }


    /**
     * 需要返回
     * {
     *     course:{
     *         List<Course>
     *
     *     }
     *     totalAmount://总价格
     * }
     *
     * @param courseId
     * @return
     */
    @GetMapping("/info/{courseId}")
    public JSONResult courseInfo(@PathVariable("courseId") String courseId){
        //Here to get course info

        /*
        * List<xxDto> dto , price totalAmount
        * */

        String[] split = courseId.split(",");
        List<Course> courses = courseService.selectBatchIds(Arrays.asList(split));
        //将String[] => List<Long>
        List<CourseMarket> courseMarkets = courseMarketMapper.selectBatchIds(Arrays.asList(split));

        List<CourseInfoVo> courseInfoVoList = new ArrayList<>();
        for(Course cours : courses){
            for(CourseMarket courseMarket : courseMarkets){
                courseInfoVoList.add(new CourseInfoVo(cours,courseMarket));
            }
        }

        CourseInfoResultVo courseInfoResultVo = new CourseInfoResultVo();
        //
        courseInfoResultVo.setCourseInfoVoList(courseInfoVoList);

        courseInfoResultVo.setTotalAmount(courseMarkets.stream().map(CourseMarket::getPrice).reduce(BigDecimal::add).get());
        return JSONResult.success(courseInfoResultVo);
    }

    @RequestMapping(value="/onLineCourse/{id}",method= RequestMethod.POST)
    public JSONResult onLineCourse(@PathVariable Long id){
        Course courseDoc = courseService.onLineCourse(id);
        return JSONResult.success(courseDoc);
    }
    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        courseService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(courseService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(courseService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseQuery query){
        Page<Course> page = new Page<Course>(query.getPage(),query.getRows());
        page = courseService.selectPage(page);
        return JSONResult.success(new PageList<Course>(page.getTotal(),page.getRecords()));
    }
}
