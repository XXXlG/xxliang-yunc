package com.xxliang.yunc.controller;

import com.xxliang.yunc.domain.PayOrder;
import com.xxliang.yunc.dto.PlaceOrderDto;
import com.xxliang.yunc.service.ICourseOrderService;
import com.xxliang.yunc.domain.CourseOrder;
import com.xxliang.yunc.query.CourseOrderQuery;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.result.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseOrder")
public class CourseOrderController {

    @Autowired
    public ICourseOrderService courseOrderService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody CourseOrder courseOrder){
        if(courseOrder.getId()!=null){
            courseOrderService.updateById(courseOrder);
        }else{
            courseOrderService.insert(courseOrder);
        }
        return JSONResult.success();
    }
    //生成订单编号......
    @PostMapping(value = "/placeOrder")
    public JSONResult placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        String orderSn = courseOrderService.placeOrder(placeOrderDto);
        return JSONResult.success(orderSn);
    }
    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        courseOrderService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(courseOrderService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(courseOrderService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody CourseOrderQuery query){
        Page<CourseOrder> page = new Page<CourseOrder>(query.getPage(),query.getRows());
        page = courseOrderService.selectPage(page);
        return JSONResult.success(new PageList<CourseOrder>(page.getTotal(),page.getRecords()));
    }

    @GetMapping(value="/UpdateById/{orderNo}")
    public JSONResult UpdateById(@PathVariable("orderNo")String  orderNo){
        CourseOrder courseOrder = courseOrderService.selectById(orderNo);
        courseOrder.setPayType(PayOrder.PAY_STATUS_PAID);
        courseOrderService.updateById(courseOrder);
        return JSONResult.success();
    }
}
