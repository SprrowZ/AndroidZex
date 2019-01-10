package com.rye.catcher.activity.fragment.orr.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @FormUrlEncoded
    @POST("{path}")
    Call<ResponseBody> postFile(@Path("path") String path);
}
