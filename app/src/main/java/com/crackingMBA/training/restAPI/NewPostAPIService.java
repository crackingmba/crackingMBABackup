package com.crackingMBA.training.restAPI;

import com.crackingMBA.training.pojo.RetrofitPostResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by vijayp on 8/9/17.
 */

public interface NewPostAPIService {
    @POST("/saveNewPostDetails.php")
    @FormUrlEncoded
    Call<RetrofitPostResponse> savePost(@Field("category") String category,
                                        @Field("postdetails") String postdetails,
                                        @Field("useremail") String useremail);
}
