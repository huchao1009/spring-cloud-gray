package com.github.opensource.gray.gateway;

import com.github.opensource.gray.GrayConstant;
import com.github.opensource.gray.GrayRequestContextHolder;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 灰度全局过滤器，用于根据请求头中的灰度标记来决定是否对请求进行灰度处理
 *
 * @author double
 * @Date 2024/7/20 17:04
 */
@AllArgsConstructor
public class GrayGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 灰度配置属性，用于获取灰度规则。
     */
    private final GrayProperties grayProperties;

    /**
     * 处理请求的过滤逻辑。
     * 如果灰度功能开启，并且请求头中包含灰度标记，则根据配置的灰度规则决定是否对请求进行灰度处理。
     * 处理过程中会修改请求头，添加灰度标记，以便后续的处理逻辑可以根据该标记进行相应的处理。
     *
     * @param exchange 当前的交换机对象，包含请求和响应信息。
     * @param chain    过滤器链，用于继续处理过滤器链中的下一个过滤器。
     * @return Mono<Void> 表示异步处理结果。
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            GrayRequestContextHolder.setGrayTag(GrayConstant.HEADER_VERSION_FLAG_BASE);
            if (Boolean.TRUE.equals(grayProperties.getEnabled())) {
                var headers = exchange.getRequest().getHeaders();
                if (headers.containsKey(GrayConstant.HEADER_VERSION_GRAY)) {
                    List<String> grayValues = headers.get(GrayConstant.HEADER_VERSION_GRAY);
                    if (CollectionUtils.isNotEmpty(grayValues) && CollectionUtils.containsAny(grayValues, GrayConstant.HEADER_VERSION_FLAG_GRAY)) {
                        // 如果匹配成功，更新灰度标记为具体的灰度版本标记
                        GrayRequestContextHolder.setGrayTag(GrayConstant.HEADER_VERSION_FLAG_GRAY);
                    }
                }
                // 修改请求头，添加灰度版本标记
                var newRequest = exchange.getRequest().mutate()
                        .header(GrayConstant.HEADER_VERSION_GRAY, GrayRequestContextHolder.getGrayTag())
                        .build();
                // 创建新的交换机对象，包含修改后的请求
                var newExchange = exchange.mutate()
                        .request(newRequest)
                        .build();
                return chain.filter(newExchange);
            }
            // 如果灰度功能未开启，直接继续处理下一个过滤器
            return chain.filter(exchange);
        } finally {
            // 请求处理完成后，清除灰度标记
            GrayRequestContextHolder.remove();
        }
    }

    /**
     * 返回过滤器的优先级，设置为最高优先级。
     * 这确保灰度过滤器在其他过滤器之前被处理。
     *
     * @return int 过滤器的优先级。
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}