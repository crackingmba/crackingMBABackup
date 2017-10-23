package com.crackingMBA.training.restAPI;

import com.crackingMBA.training.pojo.RetrofitPostResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by vijayp on 8/9/17.
 */

public interface NoticeBoardURLAPIService {
    @POST("/getNoticeBoardVideoURL.php")
    @FormUrlEncoded
    Call<RetrofitPostResponse> getNoticeBoardVideoURL(@Field("user_apk_version") String user_apk_version);
}
