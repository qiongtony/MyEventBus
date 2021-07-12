package com.example.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {

    ThreadMode mode() default ThreadMode.POSTING;
    // 接收事件优先级，值越大优先级越高
    int priority() default 0;

    // 是否粘性事件
    boolean sticky() default false;
}
