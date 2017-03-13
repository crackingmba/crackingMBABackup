package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 10-03-2017.
 */
public class Question {
    private String qnID;
    private String qnText;
    private String qnDatePosted;
    private String qnAnswer;
    private String qnAnswerDate;

    public String getQnID() {
        return qnID;
    }

    public void setQnID(String qnID) {
        this.qnID = qnID;
    }

    public String getQnText() {
        return qnText;
    }

    public void setQnText(String qnText) {
        this.qnText = qnText;
    }

    public String getQnDatePosted() {
        return qnDatePosted;
    }

    public void setQnDatePosted(String qnDatePosted) {
        this.qnDatePosted = qnDatePosted;
    }

    public String getQnAnswer() {
        return qnAnswer;
    }

    public void setQnAnswer(String qnAnswer) {
        this.qnAnswer = qnAnswer;
    }

    public String getQnAnswerDate() {
        return qnAnswerDate;
    }

    public void setQnAnswerDate(String qnAnswerDate) {
        this.qnAnswerDate = qnAnswerDate;
    }

    @Override
    public String toString() {
        return "Question{" +
                "qnID='" + qnID + '\'' +
                ", qnText='" + qnText + '\'' +
                ", qnDatePosted='" + qnDatePosted + '\'' +
                ", qnAnswer='" + qnAnswer + '\'' +
                ", qnAnswerDate='" + qnAnswerDate + '\'' +
                '}';
    }
}
