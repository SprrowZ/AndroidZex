#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_ndk_nativelib_NativeLib_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_ndk_nativelib_NativeLib_stringFromJNI2(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ file ,nativelib.cpp";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_ndk_nativelib_NativeLib_stringFromJNI3( //通过JNI拿到相关信息
        JNIEnv *env,
        jobject jo/* this */) {
    //获取java类
    jclass jc = env->GetObjectClass(jo);
    //根据java类获得类中的属性
    jfieldID idText = env->GetFieldID(jc, "text"/*java里定义的属性*/, "Ljava/lang/String;"/*签名信息*/);

    jstring resetStr = env->NewStringUTF("Well~Cool");

    env->SetObjectField(jo, idText, resetStr);

    std::string hello = "Hello from jni3";
    return env->NewStringUTF(hello.c_str());
}