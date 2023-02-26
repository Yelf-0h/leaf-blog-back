package com.yecheng.leafblogback.interceptor;

/**
 * 用户上下文
 *
 * @author Yelf
 * @create 2023-02-21-17:29
 * @date 2023/02/21
 */
public class UserHolderContext {
    private static final ThreadLocal<Long> LONG_THREAD_LOCAL = new ThreadLocal<>();

    public static void saveUserId(Long userId) {
        LONG_THREAD_LOCAL.set(userId);
    }

    public static Long getUserId() {
        return LONG_THREAD_LOCAL.get();
    }

    public static void removeUserId() {
        LONG_THREAD_LOCAL.remove();
    }
}
