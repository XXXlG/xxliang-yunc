package com.xxliang.yunc.controller;

import com.xxliang.yunc.query.CourseUserLearnQuery;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.result.PageList;
import com.xxliang.yunc.service.ICourseUserLearnService;
import com.xxliang.yunc.domain.CourseUserLearn;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courseUserLearn")
public class CourseUserLearnController {

    @Autowired
    public ICourseUserLearnService courseUserLearnService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody CourseUserLearn courseUserLearn){
        if(courseUserLearn.getId()!=null){
            courseUserLearnService.updateById(courseUserLearn);
        }else{
            courseUserLearnService.insert(courseUserLearn);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        courseUserLearnService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(courseUserLearnService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(courseUserLearnService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseUserLearnQuery query){
        Page<CourseUserLearn> page = new Page<CourseUserLearn>(query.getPage(),query.getRows());
        page = courseUserLearnService.selectPage(page);
        return JSONResult.success(new PageList<CourseUserLearn>(page.getTotal(),page.getRecords()));
    }
}
