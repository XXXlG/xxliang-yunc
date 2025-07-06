

# XXLiang 项目文档

## 项目简介
本项目是一个微服务架构的在线教育平台，包含课程管理、订单处理、支付系统、用户管理、消息通知等核心模块。每个模块独立部署，通过 Feign 和 RocketMQ 实现服务间通信和消息队列处理。

## 模块说明

### xxliang-basic
基础模块，包含通用工具类和常量定义。

### yunc-api
服务间 API 接口定义，包括课程、订单、搜索、用户认证等接口。

### yunc-pojo
数据实体类定义，涵盖课程、用户、订单、支付、系统配置等模块。

### yunc-service
微服务实现模块，包含多个子服务：
- **common**: 通用服务，提供消息发送、OSS 文件上传等基础功能
- **course**: 课程管理服务，处理课程信息、章节、收藏等
- **media**: 多媒体文件处理服务，支持视频转码和分块上传
- **order**: 订单服务，处理订单生成和交易流程
- **pay**: 支付服务，集成支付宝支付
- **search**: 搜索服务，基于 Elasticsearch 实现课程搜索
- **system**: 系统管理服务，处理配置、部门、员工等
- **uaa**: 用户认证服务，实现 OAuth2 认证
- **user**: 用户服务，处理用户注册、登录、账户等信息

### yunc-support
支持模块，包含网关服务，统一处理 API 请求和 Swagger 文档聚合。

## 技术栈
- Spring Boot
- Spring Cloud
- MyBatis Plus
- Redis
- RocketMQ
- Elasticsearch
- Swagger2
- OAuth2
- Lombok
- MapStruct (用于对象映ers)

## 核心功能

### 用户系统
- 手机/邮箱注册与登录
- 用户信息管理
- 用户权限控制
- 用户成长体系

### 课程系统
- 课程信息管理
- 课程章节管理
- 课程详情、市场信息
- 课程浏览记录
- 课程收藏

### 订单与支付
- 订单生成与管理
- 支付流程处理
- 支付流水记录
- RocketMQ 事务消息保障支付一致性

### 消息通知
- 站内消息
- 邮件通知
- 短信通知

### 搜索服务
- 基于 Elasticsearch 的课程搜索
- 支持复杂查询条件

### 系统管理
- 配置管理
- 部门管理
- 员工管理
- 操作日志

## 服务间通信
通过 FeignClient 实现服务间调用，包含服务降级实现。使用 RocketMQ 实现异步消息处理和事务消息。

## 架构特点
- 微服务架构，各服务解耦
- 使用 Redis 缓存提升性能
- 文件上传采用分块处理
- 支付系统集成支付宝
- 使用 Swagger 统一 API 文档
- 全链路监控和日志记录

## 运行环境
- Java 8+
- Spring Boot 2.6+
- Redis
- RocketMQ
- MySQL
- Elasticsearch (搜索服务需要)

## 启动顺序建议
1. yunc-service-uaa (认证服务)
2. yunc-service-user (用户服务)
3. yunc-service-course (课程服务)
4. yunc-service-order (订单服务)
5. yunc-service-pay (支付服务)
6. yunc-service-media (多媒体服务)
7. yunc-service-system (系统服务)
8. yunc-service-search (搜索服务)
9. yunc-service-gateway (网关服务)

## 特色功能
- 验证码生成：支持图形验证码
- 分块上传：支持大文件分块上传
- 事务消息：保障订单支付一致性
- 用户状态管理：使用位运算管理用户状态
- 动态查询：支持通用分页查询

## 文档规范
所有接口返回统一 JSONResult 结构，包含 success、message、code 和 data 字段。

## 注意事项
- 服务配置文件主要使用 application.yml
- 每个服务都有独立的 Redis 配置
- 使用 Lombok 简化实体类编写
- MyBatis Plus 实现通用 CRUD 操作

## 服务注册
服务使用 Nacos 作为注册中心，各服务通过 Feign 进行调用。

## 安全
通过 ResourceServerConfig 实现资源服务安全控制，使用 Spring Security 进行权限管理。