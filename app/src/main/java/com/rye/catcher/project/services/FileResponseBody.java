package com.rye.catcher.project.services;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created at 2019/2/15.
 *
 * @author Zzg
 * @function:
 */
public class FileResponseBody<T> extends ResponseBody {
    /**
     * 请求体
     */
    private ResponseBody responseBody;
    /**
     * 下载回调接口
     */
    private RetrofitCallBack<T> callBack;
    private BufferedSource mBufferedSource;

    public FileResponseBody(ResponseBody requestBody,RetrofitCallBack<T> callBack){
        super();
        this.responseBody=requestBody;
        this.callBack=callBack;
    }

    /**
     * 文件类型
     * @return
     */
    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 文件长度
     * @return
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource==null){
            mBufferedSource=Okio.buffer(source(responseBody.source()));
        }
        return mBufferedSource;
    }

    /**
     * 进度监听
     * @param source
     * @return
     */
    private Source source(Source source){
        return new ForwardingSource(source) {
            long totalBytesRead=0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead=sink.read(sink,byteCount);
                totalBytesRead+=bytesRead!=-1?bytesRead:0;
                callBack.onLoading(responseBody.contentLength(),totalBytesRead);
                return super.read(sink, byteCount);
            }
        };
    }
}
