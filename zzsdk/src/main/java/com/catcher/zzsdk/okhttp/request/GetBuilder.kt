package com.catcher.zzsdk.okhttp.request

import android.net.Uri

/**
 *@author admin
 *@function
 */
class GetBuilder:OkHttpRequestBuilder<GetBuilder>() {
    /**
     *构建Call对象
     */
    override fun build(): RequestCall {
        return GetRequest(url).build()
    }

    protected fun appendParams(url: String?, params: Map<String, String>?): String? {
        if (url == null || params == null || params.isEmpty()) {
            return url
        }
        val builder = Uri.parse(url).buildUpon()
        val keys = params.keys
        val iterator = keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            builder.appendQueryParameter(key, params[key])
        }
        return builder.build().toString()
    }
}