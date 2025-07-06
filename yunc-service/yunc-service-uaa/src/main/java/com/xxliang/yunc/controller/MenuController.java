package com.xxliang.yunc.controller;

import com.xxliang.yunc.query.MenuQuery;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.result.PageList;
import com.xxliang.yunc.service.IMenuService;
import com.xxliang.yunc.domain.Menu;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    public IMenuService menuService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody Menu menu){
        if(menu.getId()!=null){
            menuService.updateById(menu);
        }else{
            menuService.insert(menu);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        menuService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(menuService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @PreAuthorize( "hasAuthority('menu:list')")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(menuService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody MenuQuery query){
        Page<Menu> page = new Page<Menu>(query.getPage(),query.getRows());
        page = menuService.selectPage(page);
        return JSONResult.success(new PageList<Menu>(page.getTotal(),page.getRecords()));
    }
}
