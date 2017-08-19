package com.crackingMBA.training.restAPI;
import com.crackingMBA.training.pojo.RetrofitForumCommentList;
import com.crackingMBA.training.pojo.RetrofitPrepContent;
import com.crackingMBA.training.pojo.RetrofitPrepContentList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface PrepContentAPIService {
    @GET("/getPrepContent.php")
    Call<RetrofitPrepContentList> fetchPrepContent(@Query("category") String category);
}