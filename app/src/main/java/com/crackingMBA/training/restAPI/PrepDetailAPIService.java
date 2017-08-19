package com.crackingMBA.training.restAPI;
import com.crackingMBA.training.pojo.RetrofitPrepContentList;
import com.crackingMBA.training.pojo.RetrofitPrepDetailList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrepDetailAPIService {
    @GET("/getPrepDetail.php")
    Call<RetrofitPrepDetailList> fetchPrepDetail(@Query("category_id") String category_id);
}