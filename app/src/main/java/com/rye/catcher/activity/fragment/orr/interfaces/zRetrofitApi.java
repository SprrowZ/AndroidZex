package com.rye.catcher.activity.fragment.orr.interfaces;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
public interface zRetrofitApi {
    /**
     * post提交jsonString给服务器,Body不能和FormUrlEncoded联合使用
     * @param path
     * @param user
     * @param pass
     * @param postBean
     * @return
     */
    @POST("{path}")
    Call<ResponseBody> postMessage(@Path("path") String path,
                                   @Query("userName") String user,
                                   @Query("password") String pass,
                                   @Body PostBean postBean);

    /**
     * 上传文件 By MultipartBody.Part
     * @param path
     * @param file
     * @return
     */
    @Multipart
    @POST("{path}")
    Call<ResponseBody> uploadFile(@Path("path") String path,
                                  @Query("userName") String userName,
                                  @Query("password") String password,
                                  @Part MultipartBody.Part file);

    /**
     * 上传文件 By MultipartBody
     * @param path
     * @param userName
     * @param password
     * @param file mPhoto要与服务器保持一致
     * @return
     */

    @Multipart
    @POST("{zzg}")
    Call<ResponseBody> uploadFileEx(@Path("zzg") String path,
                                    @Query("userName") String userName,
                                    @Query("password") String password,
                                    @Part("mPhoto")RequestBody file);

    /**
     * 下载文件，需要完整的Url，这样使用@Url拼接Url，为啥不是@Path呢？
      * @param url
     * @return
     */
    @POST
    Call<ResponseBody> downloadFile(@Url String url);
}
