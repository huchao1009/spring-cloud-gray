package com.github.opensource.gray.feign;

import com.github.opensource.gray.GrayConstant;
import com.github.opensource.gray.GrayRequestContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;


/**
 * RPC框架OpenFeign改造，实现OpenFeign拦截器，支持从Holder中取出灰度标记，并且放到调用下游服务的请求头中，将灰度标记传递下去。
 * @author double
 * @Date 2024/7/20 17:10
 */
public class GrayFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 如果版本灰度标记为gray，将灰度标记通过HttpHeader传递下去
        if (GrayConstant.HEADER_VERSION_FLAG_GRAY.equals(GrayRequestContextHolder.getGrayTag())) {
            template.header(GrayConstant.HEADER_VERSION_GRAY, GrayConstant.HEADER_VERSION_FLAG_GRAY);
        }
    }
}
