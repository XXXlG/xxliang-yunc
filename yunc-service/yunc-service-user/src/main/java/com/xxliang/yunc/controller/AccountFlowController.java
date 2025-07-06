package com.xxliang.yunc.web.controller;

import com.xxliang.yunc.query.AccountFlowQuery;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.result.PageList;
import com.xxliang.yunc.service.IAccountFlowService;
import com.xxliang.yunc.domain.AccountFlow;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accountFlow")
public class AccountFlowController {

    @Autowired
    public IAccountFlowService accountFlowService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody AccountFlow accountFlow){
        if(accountFlow.getId()!=null){
            accountFlowService.updateById(accountFlow);
        }else{
            accountFlowService.insert(accountFlow);
        }
        return JSONResult.success();
    }

    /**
    * 删除对象
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id){
        accountFlowService.deleteById(id);
        return JSONResult.success();
    }

    /**
   * 获取对象
   */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id")Long id){
        return JSONResult.success(accountFlowService.selectById(id));
    }


    /**
    * 查询所有对象
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONResult list(){
        return JSONResult.success(accountFlowService.selectList(null));
    }


    /**
    * 带条件分页查询数据
    */
    @RequestMapping(value = "/pagelist",method = RequestMethod.POST)
    public JSONResult page(@RequestBody AccountFlowQuery query){
        Page<AccountFlow> page = new Page<AccountFlow>(query.getPage(),query.getRows());
        page = accountFlowService.selectPage(page);
        return JSONResult.success(new PageList<AccountFlow>(page.getTotal(),page.getRecords()));
    }
}
