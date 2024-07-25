package com.github.opensource.gray.loadbalancer;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * 配置LoadBalancerClient
 * @author double
 * @Date 2024/7/25 10:01
 */
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = GrayLoadBalancerClientConfiguration.class)
public class GrayLoadBalancerAutoConfiguration {
}
