package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 06-02-2017.
 */
public class MockTestQuestion {

    private String testId;
    private String qstnNo;
    private String qstnTxt;
    private String qstnFormula;
    private String qstnImage;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String selectedOption;
    private String answer;
    private String explanation;

    public String getAnswerExplanation() {
        return explanation;
    }

    public String getQstnFormula() {return qstnFormula;}
    public String getQstnImage() {return qstnImage;}
    public String getQstnNo() {
        return qstnNo;
    }
    public String getQstnTxt() {
        return qstnTxt;
    }
    public String getOption1() {
        return option1;
    }
    public String getOption2() {
        return option2;
    }
    public String getOption3() {
        return option3;
    }
    public String getOption4() {
        return option4;
    }
    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getAnswer() {
        String answerTxt = null;
        switch (answer){
            case "1" :
                answerTxt = option1;
                break;
            case "2" :
                answerTxt = option2;
                break;
            case "3" :
                answerTxt = option3;
                break;
            case "4" :
                answerTxt = option4;
                break;
        }
        return answerTxt;
    }

    @Override
    public String toString() {
        return "MockTestQuestion{" +
                "testId='" + testId + '\'' +
                ", qstnNo='" + qstnNo + '\'' +
                ", qstnTxt='" + qstnTxt + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", selectedOption='" + selectedOption + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}

