package com.crackingMBA.training.restAPI;

import com.crackingMBA.training.pojo.RetrofitForumCommentList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForumCommentsAPIService {
    @GET("/getForumPostComments.php")
    Call<RetrofitForumCommentList> fetchPostComments(@Query("post_id") String tags);
}
