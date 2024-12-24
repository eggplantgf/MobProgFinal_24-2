package com.example.mobprogfinal_v1.models;

import java.util.Date;

public class Comment {
    private String id;
    private String postId;
    private String userId;
    private String content;
    private Date datetime;

    public Comment() {
        // Firebase requires a no-argument constructor
    }

    public Comment(String id, String postId, String userId, String content, Date datetime) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
