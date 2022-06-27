#此变量表示源文件在的位置
LOCAL_PATH := $(call my-dir)
#用于清除部分LOCAL_变量
include $(CLEAR_VARS)

#配置库生成路径
#输出目录 ../jniLibs/
NDK_APP_DST_DIR=../jniLibs/${TARGET_ARCH_ABI}


#module名称，即库文件名称
LOCAL_MODULE:= haha

#源文件列表
LOCAL_SRC_FILES := haha.cpp

#收集LOCAL_XXX变量中配置的模块的相关信息
#指定了编译目标库类型（静态、动态）
include ${BUILD_SHARED_LIBRARY}