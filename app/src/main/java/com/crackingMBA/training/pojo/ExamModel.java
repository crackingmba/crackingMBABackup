package com.crackingMBA.training.pojo;

import java.util.ArrayList;

/**
 * Created by vijayp on 7/5/17.
 */
public class ExamModel {

    private ArrayList<Exam> ExamList;

    public ArrayList<Exam> getExamList ()
    {
        return ExamList;
    }

    public void setExamList (ArrayList<Exam> ExamList)
    {
        this.ExamList = ExamList;
    }


    @Override
    public String toString() {
        return "ExamModel{" +
                "ExamList=" + ExamList +
                '}';
    }
}
