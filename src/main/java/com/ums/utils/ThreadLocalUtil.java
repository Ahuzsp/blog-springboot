package com.ums.utils;

public class ThreadLocalUtil {
    // 使用泛型版本的 ThreadLocal
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    // 根据键获取值，避免类型转换警告
    @SuppressWarnings("unchecked")
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    // 存储键值对
    public static <T> void set(T value) {
        THREAD_LOCAL.set(value);
    }

    // 清除ThreadLocal 防止内存泄漏
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}