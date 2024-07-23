package com.github.opensource.gray.web;

import com.github.opensource.gray.GrayConstant;
import com.github.opensource.gray.GrayRequestContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 灰度处理拦截器，用于处理请求中的灰度逻辑。
 * <p>
 * 该拦截器会在请求处理前检查请求头中是否包含灰度标记，如果存在则将灰度标记保存到线程上下文中，
 * 以便后续业务逻辑可以根据此标记进行相应的灰度处理。
 * <p>
 * 在请求处理完成后，会清除线程上下文中的灰度标记，确保线程资源的清理。
 *
 * @author double
 * @Date 2024/7/20 17:09
 */
@SuppressWarnings("all")
public class GrayHandlerInterceptor implements HandlerInterceptor {

    /**
     * 在请求处理之前进行判断和处理。
     * <p>
     * 主要功能是检查请求头中是否包含灰度标记，如果存在则将该标记保存到线程上下文中。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器对象
     * @return 始终返回true，表示拦截器链应该继续执行。
     * @throws Exception 如果处理过程中出现异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取灰度标记
        String gray = request.getHeader(GrayConstant.HEADER_VERSION_GRAY);
        // 如果灰度标记存在且为指定的灰度值，则设置线程上下文中的灰度标记
        // 如果HttpHeader中灰度标记为true，则将灰度标记放到holder中，如果需要就传递下去
        if (gray != null && gray.equals(GrayConstant.HEADER_VERSION_FLAG_GRAY)) {
            GrayRequestContextHolder.setGrayTag(GrayConstant.HEADER_VERSION_FLAG_GRAY);
        }
        // 允许请求继续处理
        return true;
    }

    /**
     * 请求处理完成后，但在视图渲染前执行。
     * <p>
     * 该方法当前未实现具体逻辑，可根据后续需求进行扩展。
     *
     * @param request      请求对象
     * @param response     响应对象
     * @param handler      处理器对象
     * @param modelAndView 视图模型对象
     * @throws Exception 如果处理过程中出现异常
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 空实现，预留扩展点
    }

    /**
     * 请求处理完成后，无论成功还是异常，都会执行。
     * <p>
     * 主要功能是清除线程上下文中的灰度标记，确保线程资源的清理。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器对象
     * @param ex       异常对象，如果请求处理过程中没有异常，则为null
     * @throws Exception 如果处理过程中出现异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除线程上下文中的灰度标记
        GrayRequestContextHolder.remove();
    }
}