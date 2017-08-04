package com.crackingMBA.training.pojo;

import java.util.List;

/**
 * Created by vijayp on 8/4/17.
 */

public class RetrofitQuestionList {
    List<RetrofitQuestion> items;

    public List<RetrofitQuestion> getQuestions() {
        return items;
    }

    public void setItems(List<RetrofitQuestion> items) {
        this.items = items;
    }
}
