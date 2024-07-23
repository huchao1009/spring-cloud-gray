package com.github.opensource.gray.gateway;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该配置类用于启用和配置灰度流量的网关全局过滤器。
 * @author double
 * @Date 2024/7/22 20:02
 */
@ConditionalOnClass(value = GlobalFilter.class)
@EnableConfigurationProperties(GrayProperties.class)
@Configuration
public class GrayGlobalFilterConfiguration {

    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.gray.enabled", havingValue = "true")
    @Bean
    public GrayGlobalFilter grayFilter(GrayProperties grayProperties) {
        return new GrayGlobalFilter(grayProperties);
    }
}
