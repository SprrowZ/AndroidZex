package com.dawn.zgstep.demos.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created By RyeCatcher
 * at 2019/10/28
 * 仿照ButterKnife--OnClick
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnClick {
    int[]  value();
}
