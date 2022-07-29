package com.ndk.nativelib;

public class NativeLib {
    private String text = "hah";
    private int num = 0;
    // Used to load the 'nativelib' library on application startup.
    static {
        System.loadLibrary("haha");
    }

    public String getText() {
        return text;
    }


    /**
     * A native method that is implemented by the 'nativelib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String stringFromJNI2();

    public native String stringFromJNI3();
}