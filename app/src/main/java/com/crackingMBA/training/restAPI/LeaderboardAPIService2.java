package com.crackingMBA.training.restAPI;
import com.crackingMBA.training.pojo.RetrofitLeaderboardList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LeaderboardAPIService2 {
    @GET("/getLeaderboard.php")
    Call<RetrofitLeaderboardList> fetchLeaderboard(@Query("exam_id") String exam_id);
}