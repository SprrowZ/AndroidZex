package com.rye.router_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by rye
 * at 2021/1/23
 *
 * @description: 引用Router是不光需要引入Router module;
 * 还应该使用annotationProcessor 引入注解处理器，kotlin的是kapt
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Route {
    String value();//todo  改造成数组!
}
