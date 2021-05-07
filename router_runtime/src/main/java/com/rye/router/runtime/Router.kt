package com.rye.router.runtime

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log

/**
 * Create by rye
 * at 2021/3/1
 * @description:
 */
object Router {
    private const val TAG = "RouterTAG"

    //编译期间生成的总映射表
    private const val GENERATED_MAPPING = "com.dawn.come.mapping.generated.RouterMapping"

    //存储所有映射表信息
    private val mapping: HashMap<String, String> = HashMap()

    @JvmStatic
    fun init() {
        try {
            val clazz = Class.forName(GENERATED_MAPPING)
            val method = clazz.getMethod("get")
            val allMapping = method.invoke(null) as? Map<String, String>
            allMapping?.run {
                if (size > 0) {
                    onEach {
                        Log.i(TAG, "${it.key} -> ${it.value}")
                    }
                    mapping.putAll(this)
                }
            }


        } catch (e: Throwable) {
            Log.e(TAG, "init router error:$e")
        }
    }
    @JvmStatic
    fun routeTo(context: Context, url: String) {
        if (context == null || url == null) {
            Log.e(TAG, "routeTo params error: context->$context,url->$url")
            return
        }
        var targetActivityClass = ""
        //解析url,ex:router//page-main?from="outer"&target="soul"
        val uri = Uri.parse(url)
        val scheme = uri.scheme
        val host = uri.host
        val path = uri.path
        mapping.forEach {
            val itemUri = Uri.parse(it.key)
            val itemScheme = itemUri.scheme
            val itemHost = itemUri.host
            val itemPath = itemUri.path
            val match = scheme == itemScheme
                    && host == itemHost
                    && path == itemPath
            if (match) {
                targetActivityClass = it.value
                return@forEach
            }
        }
        if (targetActivityClass.isEmpty()) {
            Log.e(TAG, "router not found destination")
            return
        }

        //解析URL里的参数，封装成一个Bundle
        val bundle = Bundle()
        val query = uri.query
        query?.let {
            if (it.length < 3) return@let  //最小长度为a=b，三个字符
            val args = it.split("&")
            args.forEach { arg ->
                val splits = arg.split("=")
                bundle.putString(splits[0], splits[1])
            }
        }

        //打开对应的Activity，并传入参数
        try {
            val targetActivity = Class.forName(targetActivityClass)
            val intent = Intent(context, targetActivity)
            intent.putExtras(bundle)
            context.startActivity(intent)
        } catch (e: Throwable) {
            Log.e(TAG, "error while start activity :$e")
        }


    }


}