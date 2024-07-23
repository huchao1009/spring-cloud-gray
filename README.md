# spring-cloud-gray
基于spring-cloud实现的灰度发布

## 架构设计

在微服务架构中，接口的调用通常是服务消费方按照某种负载均衡策略去选择服务实例；但这无法满足线上更特殊化的一些路由逻辑，比如根据一次请求携带的请求头中的信息路由到某一个服务实例上。Spring Cloud Gray正是为此而创建。<br/>

### 灰度标识传递
●在使用 Nacos 作为服务发现的业务系统中，一般是需要业务根据其使用的微服务框架来决定打标方式。
●如果 Java 应用使用的 Spring Cloud 微服务开发框架，我们可以为业务容器添加对应的环境变量来完成标签的添加操作。
●比如我们希望为节点添加版本灰度标，那么为业务容器添加spring.cloud.nacos.discovery.metadata.version=gray，这样框架向Nacos注册该节点时会为其添加一个标签verison=gray。


## 工程模块
功能模块

模块 | 描述
--- | ---
spring-cloud-gray-core | 框架核心包
spring-cloud-gray-core | 灰度数据模型/Java Bean定义，client端和server端通用
spring-cloud-gray-loadbalancer | 灰度客户端与spring cloud loadbalancer集成的插件
spring-cloud-gray-openfeign | 灰度客户端与spring cloud openfeign集成的插件
spring-cloud-gray-gateway | 灰度客户端与spring cloud gateway集成的插件

## 项目扩展
项目已经实现了灰度的内核，如果要与其它的注册中心或者负载均衡中间件集成，只需实现相应的组件即可，spring cloud gray已经提供了loadbalancer、feign以及spring cloud gateway的组件，添加相应的组件依赖即可。

## 版本支持
spring-cloud版本：2023.0.1
spring-boot版本：3.2.4
spring-cloud-alibaba版本：2023.0.1.2


### 构建命令
mvn clean install -Dmaven.test.skip=true
