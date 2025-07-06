package com.xxliang.yunc.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.xxliang.yunc.domain.Employee;
import com.xxliang.yunc.domain.Login;
import com.xxliang.yunc.query.EmployeeQuery;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.result.PageList;
import com.xxliang.yunc.service.IEmployeeService;
import com.xxliang.yunc.utils.LoginContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Security;

@RestController
@RequestMapping("/employee")
@Api(tags = "员工管理接口")
public class EmployeeController {

    @Autowired
    public IEmployeeService employeeService;

    /**
     * 保存和修改公用的
     */
    @ApiOperation(value = "保存和修改公用的")
    @RequestMapping(value = "/save", method = RequestMethod.POST)

    public JSONResult saveOrUpdate(@RequestBody @Valid Employee employee) {
        if (employee.getId() != null) {
            employeeService.updateById(employee);
        } else {
            employeeService.insert(employee);
        }
        return JSONResult.success();
    }

    /**
     * 删除对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
        return JSONResult.success();
    }

    /**
     * 获取对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id") Long id) {
        return JSONResult.success(employeeService.selectById(id));
    }

    /**
     * 查询所有对象
     */
    @PreAuthorize( "hasAuthority('employee:list')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONResult list() {
        //用户上下文获取用户信息
        Login login = LoginContext.getLogin();
        System.out.println(login);
        return JSONResult.success(employeeService.selectList(null));
    }

    /**
     * 带条件分页查询数据
     */
    @PreAuthorize( "hasAuthority('employee:pagelist')")
    @RequestMapping(value = "/pagelist", method = RequestMethod.POST)
    public JSONResult page(@RequestBody EmployeeQuery query) {
        Page<Employee> page = new Page<Employee>(query.getPage(), query.getRows());
        page = employeeService.selectPage(page);
        return JSONResult.success(new PageList<Employee>(page.getTotal(), page.getRecords()));
    }
}
