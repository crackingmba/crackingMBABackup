package com.crackingMBA.training.restAPI;

import com.crackingMBA.training.pojo.RetrofitPostResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by vijayp on 8/9/17.
 */

public interface NewCommentAPIService {
    @POST("/saveNewCommentDetails.php")
    @FormUrlEncoded
    Call<RetrofitPostResponse> saveNewComment(@Field("post_id") String post_id,
                                           @Field("comment_details") String comment_details,
                                           @Field("posted_by_id") String posted_by_id);
}
