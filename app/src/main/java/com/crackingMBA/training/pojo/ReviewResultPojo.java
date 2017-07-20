package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 06-02-2017.
 */
public class ReviewResultPojo {
    private String questionTxt;
    private String selectedAnswerTxt;
    private String correctAnswerTxt;
    private String answerExplanation;

    public String getAnswerExplanation() {
        return answerExplanation;
    }

    public ReviewResultPojo(String questionTxt, String selectedAnswerTxt, String correctAnswerTxt, String answerExplanation) {
        this.questionTxt = questionTxt;
        this.selectedAnswerTxt = selectedAnswerTxt;
        this.correctAnswerTxt = correctAnswerTxt;
        this.answerExplanation=answerExplanation;
    }

    public String getQuestionTxt() {
        return questionTxt;
    }
    public String getSelectedAnswerTxt() {
        return selectedAnswerTxt;
    }
    public String getCorrectAnswerTxt() {
        return correctAnswerTxt;
    }
    @Override
    public String toString()
    {
        return "ReviewResultPojo [questionTxt = "+questionTxt+", selectedAnswerTxt = "+selectedAnswerTxt+", correctAnswerTxt = "+correctAnswerTxt+"]";
    }
}
