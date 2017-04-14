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

    public MockTestQuestion(){}

    public MockTestQuestion(String testId, String qstnNo, String qstnTxt, String qstnFormula, String qstnImage, String option1, String option2, String option3, String option4, String selectedOption, String answer) {
        this.testId = testId;
        this.qstnNo = qstnNo;
        this.qstnTxt = qstnTxt;
        this.qstnFormula = qstnFormula;
        this.qstnImage = qstnImage;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.selectedOption = selectedOption;
        this.answer = answer;
    }

    public String getQstnFormula() {return qstnFormula;}

    public void setQstnFormula(String qstnFormula) {this.qstnFormula = qstnFormula;}

    public String getQstnImage() {return qstnImage;}

    public void setQstnImage(String qstnImage) {this.qstnImage = qstnImage;}

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getQstnNo() {
        return qstnNo;
    }

    public void setQstnNo(String qstnNo) {
        this.qstnNo = qstnNo;
    }

    public String getQstnTxt() {
        return qstnTxt;
    }

    public void setQstnTxt(String qstnTxt) {
        this.qstnTxt = qstnTxt;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
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

    public void setAnswer(String answer) {
        this.answer = answer;
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

