package com.rye.base.network
/**
 * 请求地址，及其他网络相关信息
 *
 */
class Gateway {
companion object{
     private const val baseUrl="www.bilibili.com"
    fun getBaseUrl():String{
        return baseUrl
    }
}
}