package com.crackingMBA.training.restAPI;

import com.crackingMBA.training.pojo.RetrofitPostResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;






public interface ServerKeyAPIService {
    @POST("/getKeyFromServer.php")
    @FormUrlEncoded
    Call<RetrofitPostResponse> getKeyFromServer(@Field("email") String email);
}
