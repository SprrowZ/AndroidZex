package com.dawn.zgstep.ui.fragment

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.widget.TextView
import com.dawn.zgstep.R
import com.dawn.zgstep.pluggable.HookUtil
import com.rye.base.BaseFragment

/**
 * Create by rye
 * at 2021/1/14
 * @description:
 */
class ClassLoaderFragment : BaseFragment() {
    companion object {
        fun create(): ClassLoaderFragment {
            return ClassLoaderFragment()
        }

        private const val PLUGIN_DEMO_CLASS = "com.dream.pluginz.demos.PluginZDemo"
        private const val COMPONENT_DEMO_CLASS = "com.rye.catcher.ThirdSdk"
    }

    private var mReflect: TextView? = null
    private var mMergeDex: TextView? = null
    private var mHookActivity: TextView? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_class_loader
    }

    override fun initWidget() {
        super.initWidget()
        mReflect = view?.findViewById(R.id.reflect)
        mMergeDex = view?.findViewById(R.id.merge_dex)
        mHookActivity = view?.findViewById(R.id.hook_activity)
    }

    override fun initEvent() {
        super.initEvent()
        mReflect?.setOnClickListener {
            //reflectComponent()
            reflectPlugin()
        }
        mMergeDex?.setOnClickListener {
            mergeDex()
        }

        mHookActivity?.setOnClickListener {
            HookUtil.testHookAms(activity)

            val intent = Intent()
            intent.component  = ComponentName("com.dawn.zgstep.ui.activity",
                    "com.dawn.zgstep.ui.activity.ProxyActivity")
            activity?.startActivity(intent)
        }
    }

    private fun reflectComponent() {  //反射组件中的类，成功，同一个dex
        try {
            val componentClass = Class.forName(COMPONENT_DEMO_CLASS)
            Log.i("Rye", "获取到组件中的类:" + componentClass.name)
            val method = componentClass.getDeclaredMethod("printInfo", String::class.java)
            method.isAccessible = true
            //别忘了创建实例！
            val instance = componentClass.newInstance()
            method.invoke(instance, "二狗")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun reflectPlugin() {  //反射插件中的类，失败，不是同一个dex
        try {
            val pluginClazz = Class.forName(PLUGIN_DEMO_CLASS)
            Log.i("Rye", "获取到插件中的类:" + pluginClazz.name)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun mergeDex() {

    }
}