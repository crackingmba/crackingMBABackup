package com.crackingMBA.training.restAPI;
import com.crackingMBA.training.pojo.RetrofitPrepContentList;
import com.crackingMBA.training.pojo.RetrofitPrepHLContentList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrepHLContentAPIService {
    @GET("/getPrepHLContent.php")
    Call<RetrofitPrepHLContentList> fetchPrepHLContent(@Query("category") String category);
}