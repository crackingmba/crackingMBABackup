package com.crackingMBA.training.pojo;

import java.util.List;

/**
 * Created by MSK on 10-03-2017.
 */
public class RegisrationResponseObject {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RegisrationResponseObject{" +
                "staus='" + status + '\'' +
                '}';
    }
}
