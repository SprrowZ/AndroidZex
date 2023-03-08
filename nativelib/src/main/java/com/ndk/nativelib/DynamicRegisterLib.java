package com.ndk.nativelib;

public class DynamicRegisterLib {

    static{
        System.loadLibrary("nativelib");
    }

    public static native int add(int a,int b);
    public static native int sub(int a,int b);
    public static native int mul(int a,int b);
    public static native int div(int a,int b);

}
