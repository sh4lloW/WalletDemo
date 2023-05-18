package com.example.wallet.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {

    }

    public static <T> T copyBean(Object source, Class<T> clazz){
        // 创建目标对象
        T obj = null;
        try {
            // 通过反射获取
            obj = clazz.newInstance();
            // 实现copy
            BeanUtils.copyProperties(source, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> List<T> copyBeanList(List<?> list, Class<T> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
