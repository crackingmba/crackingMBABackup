package com.crackingMBA.training.pojo;

import java.util.ArrayList;

/**
 * Created by MSK on 06-02-2017.
 */
public class SubCategories
{
    private String subCatTitle;

    private ArrayList<SubCatList> subCatList;

    public String getSubCatTitle ()
    {
        return subCatTitle;
    }

    public void setSubCatTitle (String subCatTitle)
    {
        this.subCatTitle = subCatTitle;
    }

    public ArrayList<SubCatList> getSubCatList ()
    {
        return subCatList;
    }

    public void setSubCatList (ArrayList<SubCatList> subCatList)
    {
        this.subCatList = subCatList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subCatTitle = "+subCatTitle+", subCatList = "+subCatList+"]";
    }
}
