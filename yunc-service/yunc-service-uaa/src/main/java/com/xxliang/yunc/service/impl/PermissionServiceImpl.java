package com.xxliang.yunc.service.impl;

import com.xxliang.yunc.domain.Permission;
import com.xxliang.yunc.mapper.PermissionMapper;
import com.xxliang.yunc.service.IPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Autowired
     private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissByLoginId(Long loginId) {
        return permissionMapper.getPermissByLoginId(loginId);
    }
}
