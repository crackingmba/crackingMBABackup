package com.crackingMBA.training.restAPI;

import com.crackingMBA.training.pojo.RetrofitPostResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by vijayp on 8/9/17.
 */

public interface LoginAPIService {
    @POST("/validateLoginDetails.php")
    @FormUrlEncoded
    Call<RetrofitPostResponse> validateLogin(@Field("email") String email,
                                        @Field("password") String password);
}
