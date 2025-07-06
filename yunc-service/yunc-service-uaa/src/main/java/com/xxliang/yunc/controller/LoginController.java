package com.xxliang.yunc.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.xxliang.yunc.domain.Login;
import com.xxliang.yunc.dto.LoginDto;
import com.xxliang.yunc.dto.RefreshTokenDto;
import com.xxliang.yunc.query.LoginQuery;
import com.xxliang.yunc.result.JSONResult;
import com.xxliang.yunc.result.PageList;
import com.xxliang.yunc.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public ILoginService loginService;

    //登录成功后重定向地址
    @RequestMapping("/success")
    @ResponseBody
    public String loginSuccess() {
        return "登录成功";
    }

    //登录成功后获取Token信息。
    @PostMapping("/common")
    public JSONResult common(@RequestBody @Valid LoginDto loginDto) {
        return JSONResult.success(loginService.common(loginDto));
    }

    @PostMapping("/refresh")
    public JSONResult refresh(@RequestBody @Valid RefreshTokenDto refreshTokenDto) {
        return JSONResult.success(loginService.refresh(refreshTokenDto));
    }

    /**
     * 保存和修改公用的
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONResult saveOrUpdate(@RequestBody Login login) {
        if (login.getId() != null) {
            loginService.updateById(login);
        } else {
            loginService.insert(login);
        }
        return JSONResult.success();
    }

    @RequestMapping(value = "/phoneRegister", method = RequestMethod.POST)
    public JSONResult phoneRegister(@RequestBody Login login) {
        loginService.insert(login);//返回一个LoginID
        return JSONResult.success(login.getId());
    }

    /**
     * 删除对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JSONResult delete(@PathVariable("id") Long id) {
        loginService.deleteById(id);
        return JSONResult.success();
    }

    /**
     * 获取对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JSONResult get(@PathVariable("id") Long id) {
        return JSONResult.success(loginService.selectById(id));
    }


    /**
     * 查询所有对象
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONResult list() {
        return JSONResult.success(loginService.selectList(null));
    }


    /**
     * 带条件分页查询数据
     */
    @RequestMapping(value = "/pagelist", method = RequestMethod.POST)
    public JSONResult page(@RequestBody LoginQuery query) {
        Page<Login> page = new Page<Login>(query.getPage(), query.getRows());
        page = loginService.selectPage(page);
        return JSONResult.success(new PageList<Login>(page.getTotal(), page.getRecords()));
    }
}
