package com.rye.catcher.utils

import com.rye.base.utils.FileUtils

const val crashHeader="UncaughtException:"

//kotlin，私有构造器
class CrashHandler private constructor():Thread.UncaughtExceptionHandler {
    //静态内部类单例模式
    companion object{
        val instance=SingleHolder().holder
    }
    class SingleHolder{
        val holder=CrashHandler()
    }
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        FileUtil.writeUserLog(crashHeader+t.hashCode()+",,"+e.toString())
    }
}