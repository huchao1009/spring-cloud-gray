package com.github.opensource.gray.gateway;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author double
 * @Date 2024/7/20 17:06
 */
//@Configuration
@RefreshScope
@ConfigurationProperties("spring.cloud.loadbalancer.gray")
@Data
public class GrayProperties {

    /**
     * 灰度开关
     */
    private Boolean enabled;

    /**
     * 灰度匹配内容
     */
    private List<String> matches = new ArrayList<>();
}

