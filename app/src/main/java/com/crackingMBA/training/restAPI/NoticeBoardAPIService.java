package com.crackingMBA.training.restAPI;

import com.crackingMBA.training.pojo.RetrofitNoticeBoardList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NoticeBoardAPIService {
    @GET("/getNoticeBoardList.php")
    Call<RetrofitNoticeBoardList> fetchBoardList(@Query("board_id") String tags);
}
