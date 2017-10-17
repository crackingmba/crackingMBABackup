package com.crackingMBA.training.restAPI;
import com.crackingMBA.training.pojo.RetrofitMockTestList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface MockTestAPIService {
    @GET("/getMockTestAnalysisList.php")
    Call<RetrofitMockTestList> fetchMockTests(@Query("user_id") String tags);
}