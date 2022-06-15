package com.zzg.ndkmodule;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/6/15 9:15 上午
 */
public class JNIUtils {
    /*java调C中的方法需要用native声明且方法名必须和c的方法名一样*/
    public native static String stringFromJNI();
}
