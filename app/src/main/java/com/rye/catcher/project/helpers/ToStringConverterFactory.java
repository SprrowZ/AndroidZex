package com.rye.catcher.project.helpers;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created at 2019/3/21.
 *
 * @author Zzg
 * @function:  自定义Retrofit Converter，String 类型
 */
public class ToStringConverterFactory extends Converter.Factory {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

    /**
     * 返回结果
     * @param type
     * @param annotations
     * @param retrofit
     * @return
     */
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)){
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
        return null;
    }

    /**
     * 请求封装
     * @param type
     * @param parameterAnnotations
     * @param methodAnnotations
     * @param retrofit
     * @return
     */
    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
       if (String.class.equals(type)){
           return new Converter<String, RequestBody>() {
               @Override
               public RequestBody convert(String value) throws IOException {
                   return RequestBody.create(MEDIA_TYPE,value);
               }
           };
       }
        return  null;
    }
}
