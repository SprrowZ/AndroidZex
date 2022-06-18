#Android.mk 参数

#设置工作目录，它用于在开发tree中查找源文件。宏my-dir由Build System提供,会返回Android.mk文件所在的目录
LOCAL_PATH := $(call my-dir)

#CLEAR_VARS变量由Build System提供。指向一个指定的GNU Makefile，由它负责清理LOCAL_xxx类型文件，但不是清理LOCAL_PATH
#所有的编译控制文件由同一个GNU Make解析和执行，其变量是全局的。所以清理后才能便面相互影响,这一操作必须有
include $(CLEAR_VARS)

# LOCAL_MODULE模块必须定义，以表示Android.mk中的每一个模块，名字必须唯一且不包含空格
#Build System 会自动添加适当的前缀和后缀。例如，demo，要生成动态库，则生成libdemo.so。但请注意：如果模块名字被定义为libabd，则生成libabc.so。不再添加前缀。
LOCAL_MODULE := DEMO


#指定参与模块编译的C/C++源文件名。不必列出头文件，build System 会自动帮我们找出依赖文件。缺省的C++ 源码的扩展名为.cpp。
LOCAL_SRC_FILES := demo.c


#// BUILD_SHARED_LIBRARY是Build System提供的一个变量，指向一个GUN Makefile Script。它负责收集自从上次调用include $(CLEAR_VARS)后的所有LOCAL_xxxxinx。并决定编译什么类型
#//1. BUILD_STATIC_LIBRARY：编译为静态库
#//2. BUILD_SHARED_LIBRARY：编译为动态库
#//3. BUILD_EXECUTABLE：编译为Native C 可执行程序
#//4. BUILD_PREBUILT：该模块已经预先编译
include $(BUILD_SHARED_LIBRARY)

