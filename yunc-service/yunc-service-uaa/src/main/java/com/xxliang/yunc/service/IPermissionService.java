package com.xxliang.yunc.service;

import com.xxliang.yunc.domain.Permission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
public interface IPermissionService extends IService<Permission> {

    List<Permission> getPermissByLoginId(Long loginId);
}
