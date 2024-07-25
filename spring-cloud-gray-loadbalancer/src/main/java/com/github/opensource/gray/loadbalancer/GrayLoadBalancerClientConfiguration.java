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
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new GrayLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }
}
