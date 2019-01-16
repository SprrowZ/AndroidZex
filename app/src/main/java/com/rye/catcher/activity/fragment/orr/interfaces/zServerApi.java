package com.rye.catcher.activity.fragment.orr.interfaces;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created at 2019/1/10.
 *
 * @author Zzg
 * @function:
 */
public interface zServerApi {
    /**
     * post提交jsonString给服务器
     * @param path
     * @param user
     * @param pass
     * @param postBean
     * @return
     */
    @FormUrlEncoded
    @POST("{path}")
    Call<ResponseBody> postMessage(@Path("path") String path,
                                   @Field("userName") String user,
                                   @Field("password") String pass,
                                   @Body PostBean postBean);

    /**
     * 上传文件
     * @param path
     * @param file
     * @return
     */
    @Multipart
    @POST("{path}")
    Call<ResponseBody> uploadFile(@Path("path") String path, @Part MultipartBody.Part file);


    @POST
    Call<ResponseBody> downloadFile(@Url String url);
}
