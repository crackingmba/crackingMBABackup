package com.crackingMBA.training.pojo;
public class RetrofitQuestion {
    String id, post_details,posted_by_id, posted_by, comment_count;
    public String getTitle() {
        return post_details;
    }
    public String getPostedBy() {return posted_by;}
    public String getPostID() {return id;}
    public String getCommentCount() {return comment_count;}
    public String getPostedById() {return posted_by_id;}
}