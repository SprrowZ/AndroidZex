package com.catcher.zzsdk.okhttp.request

import okhttp3.Request
import okhttp3.RequestBody

/**
 *@author admin
 *@function
 */
class GetRequest(url: String) : BaseRequest(url) {
    override fun buildRequestBody(): RequestBody? {
        return null
    }

    /**
     * 构建Get Request对象
     */
    override fun buildRequest(requestBody: RequestBody?): Request {
        return builder.get().build()
    }
}