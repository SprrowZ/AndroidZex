package com.MutableMaprye.base.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import com.rye.base.common.LanguageConstants
import java.util.*

class LanguageUtil {
    companion object{
        const val  LANGUAGE="language"
        val  mSupportLanguage= mutableMapOf<String,Locale>(
                LanguageConstants.ENGLISH to Locale.ENGLISH,
                LanguageConstants.SIMPLIFIED_CHINESE to Locale.SIMPLIFIED_CHINESE,
                LanguageConstants.TRADITIONAL_CHINESE to Locale.TRADITIONAL_CHINESE,
                LanguageConstants.FRANCE to Locale.FRANCE,
                LanguageConstants.GERMAN to Locale.GERMAN,
                LanguageConstants.JAPAN to  Locale.JAPAN
                 )

        /**
         * 查看是否支持此语言
         */
        fun  isSupportLanguage(language:String):Boolean{
            return mSupportLanguage.containsKey(language)
        }

        /**
         * 获取支持的语言,如果没有就返回系统语言首选项
         */
        fun getSupportLanguage(language:String): Locale? {
            if (isSupportLanguage(language)){
                return mSupportLanguage.get(language)
            }
            return getSystemPreferredLanguage()
        }

        /**
         * 系统语言首选项
         */
        fun getSystemPreferredLanguage():Locale?{
            var locale:Locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){//7.0之后
                locale=LocaleList.getDefault().get(0)
            }else{
                locale=Locale.getDefault()
            }
            return locale
        }

        /**
         * 应用新语言
         */
        fun applyLanguage(context: Context,newLanguage:String ){
            val resources=context.resources
            val configuration=resources.configuration
            val locale= getSupportLanguage(newLanguage)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){//7.0之后和之前的设置是不一样的
                configuration.setLocale(locale)
            }else{
                configuration.locale=locale
                val dm=resources.displayMetrics
                resources.updateConfiguration(configuration,dm)
            }
        }

        /**
         * 关键方法
         */
        fun attachBaseContext(context:Context,language:String):Context{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){//7.0以上返回的Context
                return createConfigurationResources(context,language)
            }else{
                applyLanguage(context,language)//多余判断
                return context
            }
        }

        /**
         * 7.0以上的适配策略
          */
        fun createConfigurationResources(context:Context,language:String):Context{
            var locale:Locale?
            val resources=context.resources
            val configuration=resources.configuration
            if (TextUtils.isEmpty(language)){
                locale= getSystemPreferredLanguage()
            }else{
                locale= getSupportLanguage(language)
            }
            configuration.setLocale(locale)
            return context.createConfigurationContext(configuration)
        }
    }
}