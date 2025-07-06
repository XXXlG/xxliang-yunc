package com.xxliang.yunc.service.impl;

import com.xxliang.yunc.domain.Config;
import com.xxliang.yunc.mapper.ConfigMapper;
import com.xxliang.yunc.service.IConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author xxliang
 * @since 2025-05-29
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
