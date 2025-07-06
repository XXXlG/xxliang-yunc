package com.xxliang.yunc.service.impl;

import com.xxliang.yunc.domain.Employee;
import com.xxliang.yunc.mapper.EmployeeMapper;
import com.xxliang.yunc.service.IEmployeeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-29
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
