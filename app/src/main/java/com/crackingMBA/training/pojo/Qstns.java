package com.crackingMBA.training.pojo;

import java.util.ArrayList;

/**
 * Created by MSK on 06-02-2017.
 */
public class Qstns
{
    private String qstnId;

    private String qstn;

    private String date;

    public Qstns(){

    }

    public Qstns(String qstnId, String qstn, String date){
        this.qstnId = qstnId;
        this.qstn = qstn;
        this.date = date;
    }

    public String getQstnId() {
        return qstnId;
    }

    public void setQstnId(String qstnId) {
        this.qstnId = qstnId;
    }

    public String getQstn() {
        return qstn;
    }

    public void setQstn(String qstn) {
        this.qstn = qstn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "QsntsPojo [qstnId="+qstnId+", qstn = "+qstn+", date = "+date+"]";
    }
}
