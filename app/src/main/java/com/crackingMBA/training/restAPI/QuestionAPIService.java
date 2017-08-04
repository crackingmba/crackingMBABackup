package com.crackingMBA.training.restAPI;

import com.crackingMBA.training.pojo.RetrofitQuestionList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionAPIService {
    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")  //End Url
    Call<RetrofitQuestionList> fetchQuestions(@Query("tagged") String tags);
}
