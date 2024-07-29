package com.github.opensource.gray.loadbalancer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * 负载均衡配置类，指定使用哪一个负载均衡器
 * 参考org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration
 *
 * 不要加@Configuration，如果将负载均衡策略注册为bean这样是不行的。
 * 因为这样会导致environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);返回为null，后边调用feign时会失败。
 * 官网：<a url="https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#switching-between-the-load-balancing-algorithms"></a>
 *
 * @author double
 * @Date 2024/7/20 17:13
 */
//@ConditionalOnClass(value = ReactorServiceInstanceLoadBalancer.class)
//@Configuration(proxyBeanMethods = false)
public class GrayLoadBalancerClientConfiguration {

    /**
     * 参考默认实现
     *
     * @see org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration#reactorServiceInstanceLoadBalancer
     * @return ReactorLoadBalancer
     */
    @Bean
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.gray.enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnBean(LoadBalancerClientFactory.class)
    public ReactorLoadBalancer<ServiceInstance> grayReactorLoadBalancer(Environment environment,
                                                                        LoadBalancerClientFactory loadBalancerClientFactory) {
        //获取微服务名称
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        //注意这里注入的是 LazyProvider，这主要因为在注册这个 Bean 的时候相关的 Bean 可能还没有被加载注册，利用 LazyProvider 而不是直接注入所需的 Bean 防止报找不到 Bean 注入的错误。
        return new GrayRoundRobinLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }
}
