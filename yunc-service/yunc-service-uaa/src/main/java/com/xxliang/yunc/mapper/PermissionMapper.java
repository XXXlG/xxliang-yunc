package com.xxliang.yunc.mapper;

import com.xxliang.yunc.domain.Permission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getPermissByLoginId(@Param("loginId") Long loginId);
}
