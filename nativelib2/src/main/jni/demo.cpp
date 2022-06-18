//
// Created by zhaozhenguo on 2022/6/16.
//
#include <jni.h>
#include <string>
#include "com_zzg_nativelib2_NDKTest.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_zzg_nativelib2_NDKTest_count
        (JNIEnv *env, jobject){
    return (*env)-> NewStringUTF("HELLO NDK!");
}
