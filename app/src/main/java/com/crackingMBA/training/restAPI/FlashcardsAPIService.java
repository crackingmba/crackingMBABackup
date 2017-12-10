package com.crackingMBA.training.restAPI;
import com.crackingMBA.training.pojo.RetrofitFlashCardContentList;
import com.crackingMBA.training.pojo.RetrofitPrepContentList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlashcardsAPIService {
    @GET("/getFlashCards.php")
    Call<RetrofitFlashCardContentList> fetchFlashCards(@Query("category") String category);
}