package com.github.opensource.gray.feign;

import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类，当类路径中存在RequestInterceptor类时，启用Feign拦截器的自动配置。
 * 这个类的作用是为Feign客户端提供一种方式来添加自定义的请求拦截器，以在请求发送前进行一些额外的处理。
 *
 * @author double
 * @Date 2024/7/20 17:11
 */
@Configuration
@ConditionalOnClass(value = RequestInterceptor.class)
public class GrayFeignInterceptorAutoConfiguration {

    /**
     * 配置一个Bean，返回GrayFeignRequestInterceptor实例。
     * 这个方法的作用是创建并返回一个GrayFeignRequestInterceptor实例，作为Feign客户端的请求拦截器。
     * 通过这个拦截器，可以在Feign客户端发送请求之前，添加一些特定的请求头或者进行其他必要的处理。
     *
     * @return GrayFeignRequestInterceptor 实例，用于Feign请求的拦截。
     */
    @Bean
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.gray.enabled", havingValue = "true", matchIfMissing = true)
    public GrayFeignRequestInterceptor feignRequestInterceptor() {
        return new GrayFeignRequestInterceptor();
    }
}