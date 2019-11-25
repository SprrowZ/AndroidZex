package com.catcher.zzsdk.okhttp.request;


import org.jetbrains.annotations.NotNull;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/11/6.
 * 构建request对象，还需要builder调用封装
 */
public abstract class BaseRequest{
    protected String url;
    protected Object tag;
    //todo 线程不安全，可优化
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected int id;

    protected Request.Builder builder = new Request.Builder();

    protected BaseRequest(String url, Object tag,
                          Map<String, String> params, Map<String, String> headers, int id) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id;

        if (url == null) {
          throw  new IllegalArgumentException("url should not be null...");
        }

        initBuilder();
    }
    protected BaseRequest(String url, Map<String,String> params){
        if (url == null) {
            throw  new IllegalArgumentException("url should not be null...");
        }
        builder.url(url);
    }

    public BaseRequest(@NotNull String url) {
        builder.url(url);
    }


    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private void initBuilder() {
        builder.url(url).tag(tag);
        appendHeaders();
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    /**
     * 链式调用---
      * @return
     */
    public RequestCall build() {
        return new RequestCall(this);
    }

    /**
     * 构造Request对象，主要是Call里需要引用
     *
     * @param callback
     * @return
     */
    public Request generateRequest(Callback callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);

        //构造Request
        return buildRequest(wrappedRequestBody);
    }


    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    public int getId() {
        return id;
    }

}
