package com.github.opensource.gray;

/**
 *  该类用于管理灰度请求的上下文信息。
 *  它提供了一种在当前线程中存储和获取灰度版本标签的方法，
 *  以便在进行灰度发布时，可以根据这个标签对请求进行分流。
 *
 * @author double
 * @Date 2024/7/20 17:05
 */
public class GrayRequestContextHolder {
    /**
     * 用于存储当前线程的灰度版本标签的ThreadLocal变量。
     */
    private static final ThreadLocal<String> VERSION_GARY_TAG = new ThreadLocal<>();

    public static void setGrayTag(final String tag) {
        VERSION_GARY_TAG.set(tag);
    }

    public static String getGrayTag() {
        return VERSION_GARY_TAG.get();
    }

    /**
     * 清除当前线程的灰度版本标签
     */
    public static void remove() {
        VERSION_GARY_TAG.remove();
    }
}
