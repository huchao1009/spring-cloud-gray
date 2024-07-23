package com.github.opensource.gray.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * 配置添加灰度拦截器
 *
 * @author double
 * @Date 2024/7/20 17:10
 */
@Configuration
@ConditionalOnClass(value = WebMvcConfigurer.class)
public class GrayWebMvcAutoConfiguration {

//    /**
//     * Spring MVC 请求拦截器
//     *
//     * @return WebMvcConfigurer
//     */
//    @Bean
//    public WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(new GrayHandlerInterceptor());
//            }
//        };
//    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(Filter.class)
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.gray.enabled", havingValue = "true", matchIfMissing = true)
    public GrayFilter grayTrackFilter() {
        return new GrayFilter();
    }
}