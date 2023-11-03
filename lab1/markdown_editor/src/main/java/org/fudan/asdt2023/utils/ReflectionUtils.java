package org.fudan.asdt2023.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    /**
     * 获取clazz的所有接口，包括clazz自身实现的接口和父类实现的接口
     *
     * @param clazz 目标类
     * @return clazz的所有接口，包括clazz自身实现的接口和父类实现的接口
     */
    public static Class<?>[] getAllInterfaces(Class<?> clazz) {
        List<Class<?>> allInterfaces = new ArrayList<>();
        Class<?> cur = clazz;
        while (cur != null) {
            Class<?>[] ifs = cur.getInterfaces();
            allInterfaces.addAll(Arrays.asList(ifs));
            cur = cur.getSuperclass();
        }
        return allInterfaces.toArray(new Class<?>[0]);
    }
}
