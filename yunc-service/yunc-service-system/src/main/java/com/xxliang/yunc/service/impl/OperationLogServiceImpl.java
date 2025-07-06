package com.xxliang.yunc.service.impl;

import com.xxliang.yunc.domain.OperationLog;
import com.xxliang.yunc.mapper.OperationLogMapper;
import com.xxliang.yunc.service.IOperationLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-29
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
