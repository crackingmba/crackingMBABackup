package com.crackingMBA.training.pojo;

import java.util.List;

/**
 * Created by MSK on 10-03-2017.
 */
public class LoginResponseObject {

    private String userValid;
    private String userName;
    private List<Question> userQuestions;

    public String getUserValid() {
        return userValid;
    }

    public void setUserValid(String userValid) {
        this.userValid = userValid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Question> getUserQuestions() {
        return userQuestions;
    }

    public void setUserQuestions(List<Question> userQuestions) {
        this.userQuestions = userQuestions;
    }

    @Override
    public String toString() {
        return "LoginResponseObject{" +
                "userValid='" + userValid + '\'' +
                ", userName='" + userName + '\'' +
                ", userQuestions=" + userQuestions +
                '}';
    }
}
