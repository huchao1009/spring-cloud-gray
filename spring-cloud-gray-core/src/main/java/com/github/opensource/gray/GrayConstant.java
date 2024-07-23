package com.github.opensource.gray;

/**
 * 灰度发布版本控制常量类。
 * <p>
 * 本类定义了用于灰度发布时版本控制的HTTP头常量。这些常量被用于标识请求是否属于灰度测试的一部分。
 *
 * @author double
 * @Date 2024/7/20 17:24
 */
public class GrayConstant {

    /**
     * HTTP头字段名：版本信息。
     * 用于在请求中携带版本信息。
     */
    public final static String HEADER_VERSION_GRAY = "version";

    /**
     * 版本值：灰度。
     * 当请求头中的版本值为此常量时，表示请求是灰度测试的一部分。
     */
    public final static String HEADER_VERSION_FLAG_GRAY = "gray";

    /**
     * 版本值：基础（非灰度）。
     * 当请求头中的版本值为此常量时，表示请求是针对基础（非灰度）版本的服务。
     */
    public final static String HEADER_VERSION_FLAG_BASE = "base";
}