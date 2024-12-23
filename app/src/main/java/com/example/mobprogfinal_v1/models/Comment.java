package com.example.mobprogfinal_v1.models;

public class Comment {
    private String id;
    private String contents;
    private long datetime;
    private String postId;
    private String userId;

    public Comment(String id, String contents, long datetime, String postId, String userId) {
        this.id = id;
        this.contents = contents;
        this.datetime = datetime;
        this.postId = postId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }
}
