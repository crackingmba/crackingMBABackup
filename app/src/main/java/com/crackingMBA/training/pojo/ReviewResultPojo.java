package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 06-02-2017.
 */
public class ReviewResultPojo {

    private String questionTxt;

    private String selectedAnswerTxt;

    private String correctAnswerTxt;

    public ReviewResultPojo(){

    }

    public ReviewResultPojo(String questionTxt, String selectedAnswerTxt, String correctAnswerTxt) {
        this.questionTxt = questionTxt;
        this.selectedAnswerTxt = selectedAnswerTxt;
        this.correctAnswerTxt = correctAnswerTxt;
    }

    public String getQuestionTxt() {
        return questionTxt;
    }

    public void setQuestionTxt(String questionTxt) {
        this.questionTxt = questionTxt;
    }

    public String getSelectedAnswerTxt() {
        return selectedAnswerTxt;
    }

    public void setSelectedAnswerTxt(String selectedAnswerTxt) {
        this.selectedAnswerTxt = selectedAnswerTxt;
    }

    public String getCorrectAnswerTxt() {
        return correctAnswerTxt;
    }

    public void setCorrectAnswerTxt(String correctAnswerTxt) {
        this.correctAnswerTxt = correctAnswerTxt;
    }

    @Override
    public String toString()
    {
        return "ReviewResultPojo [questionTxt = "+questionTxt+", selectedAnswerTxt = "+selectedAnswerTxt+", correctAnswerTxt = "+correctAnswerTxt+"]";
    }
}
