package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 06-02-2017.
 */
public class SubCatList {
    private String id;
    private String thumbnail;
    private String category_name;
    private String subcat_descrip1;

    public String getSubcat_descrip1() {
        return subcat_descrip1;
    }

    public void setSubcat_descrip1(String subcat_descrip1) {
        this.subcat_descrip1 = subcat_descrip1;
    }



    private String date_range;
    public String getDate_range() {return date_range;}
    public void setDate_range(String date_range) {this.date_range = date_range;}

    private String video_yn;
    public String getVideo_yn() {return video_yn;}
    public void setVideo_yn(String video_yn) {this.video_yn = video_yn;}

    private String subcat_descrip;

    public String getSubcategory_description() {
        return subcat_descrip;
    }
    public void setSubcategory_description(String subcategory_description) {this.subcat_descrip = subcategory_description;}



    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getThumbnail ()
    {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getCategory_name ()
    {
        return category_name;
    }

    public void setCategory_name (String category_name)
    {
        this.category_name = category_name;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", thumbnail = "+thumbnail+", category_name = "+category_name+"]";
    }

}
