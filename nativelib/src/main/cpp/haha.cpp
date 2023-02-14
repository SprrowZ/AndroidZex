#include <jni.h>
#include <string>
//静态注册native方法

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
    //根据java类获得类中的属性,String类型
    jfieldID idText = env->GetFieldID(jc, "text"/*java里定义的属性*/, "Ljava/lang/String;"/*签名信息*/);

    jstring resetStr = env->NewStringUTF("Well~Cool");
    //int类型
    jfieldID  idNum = env->GetFieldID(jc,"num","I");

    env->SetObjectField(jo, idText, resetStr);
    //int 重新赋值
    env->SetIntField(jo,idNum,3000);

    //拿到Java方法
    jmethodID  idSetName = env->GetMethodID(jc,"setName","(Ljava/lang/String;)V"/*签名，要根据参数类型和返回值类型定义，
 * 如果是int类型，则签名为(I)V*/);
    jstring nameStr = env->NewStringUTF(" Bob!");
    env->CallVoidMethod(jo,idSetName,nameStr);

    std::string hello = "Hello from jni3";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_ndk_nativelib_NativeLib_stringFromJNI4(JNIEnv *env, jclass clazz) {

    jfieldID idNum1 = env->GetStaticFieldID(clazz,"num1","I");
    env->SetStaticIntField(clazz,idNum1,233);
    std::string hello = "Hello from jni4";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_ndk_nativelib_NativeLib_fun5(JNIEnv *env, jobject thiz, jint a, jfloat b, jdouble c,
                                      jboolean d, jintArray e) {
    // TODO: implement fun5()
}