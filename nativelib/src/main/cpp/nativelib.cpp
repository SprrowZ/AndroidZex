#include <jni.h>
#include <string>

//动态注册native方法

jint addNum(JNIEnv *env, jclass jc, jint a, jint b) {
    return a + b;
}

jint subNum(JNIEnv *env, jclass jc, jint a, jint b) {
    return a - b;
}

jint mulNum(JNIEnv *env, jclass jc, jint a, jint b) {
    return a * b;
}

jint divNum(JNIEnv *env, jclass jc, jint a, jint b) {
    return a / b;
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;
    //判断状态是否正确
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }
    //定义函数映射关系
    const JNINativeMethod method[] = {
            {"add", "(II)I", (void *) addNum},
            {"sub", "(II)I", (void *) subNum},
            {"mul", "(II)I", (void *) mulNum},
            {"div", "(II)I", (void *) divNum}
    };
    //找到对应的JNITool类
    jclass jClassName = env->FindClass("com/ndk/nativelib/DynamicRegisterLib");
    //开始注册
    jint ret = env->RegisterNatives(jClassName, method, 4);
    //如果注册失败
    if (ret != JNI_OK) {
        return -1;
    }
    return JNI_VERSION_1_6;
}