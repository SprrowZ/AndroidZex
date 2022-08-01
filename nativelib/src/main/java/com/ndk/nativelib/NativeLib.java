package com.ndk.nativelib;

public class NativeLib {
    private String text = "hah";
    private int num = 0;

    private String name;
    // Used to load the 'nativelib' library on application startup.
    static {
        System.loadLibrary("haha");
    }

    public String getText() {
        return text;
    }

    public int getNum(){
        return num;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    //jclass测试 ，静态代码
    public static int num1=0;


    /**
     * A native method that is implemented by the 'nativelib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String stringFromJNI2();

    public native String stringFromJNI3();

    public static native String stringFromJNI4();

    public native void fun5(int a,float b,double c,boolean d,int[] e);
}