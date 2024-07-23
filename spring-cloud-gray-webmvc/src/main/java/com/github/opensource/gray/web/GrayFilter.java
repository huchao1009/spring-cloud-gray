package com.github.opensource.gray.web;

import com.github.opensource.gray.GrayConstant;
import com.github.opensource.gray.GrayRequestContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 灰度处理过滤器，用于处理请求中的灰度逻辑。
 * <p>
 * 该过滤器会在请求处理前检查请求头中是否包含灰度标记，如果存在则将灰度标记保存到线程上下文中，
 * 以便后续业务逻辑可以根据此标记进行相应的灰度处理。
 * <p>
 * 在请求处理完成后，会清除线程上下文中的灰度标记，确保线程资源的清理。
 *
 * @author double
 * @Date 2024/7/23 10:52
 */
public class GrayFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        try {
            // 从请求头中获取灰度标记
            String gray = request.getHeader(GrayConstant.HEADER_VERSION_GRAY);
            // 如果灰度标记存在且为指定的灰度值，则设置线程上下文中的灰度标记
            // 如果HttpHeader中灰度标记为true，则将灰度标记放到holder中，如果需要就传递下去
            if (gray != null && gray.equals(GrayConstant.HEADER_VERSION_FLAG_GRAY)) {
                GrayRequestContextHolder.setGrayTag(GrayConstant.HEADER_VERSION_FLAG_GRAY);
            }
            chain.doFilter(request, response);
        } finally {
            // 清除线程上下文中的灰度标记
            GrayRequestContextHolder.remove();
        }
    }

    @Override
    public void destroy() {

    }
}
