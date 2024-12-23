package com.example.mobprogfinal_v1.models;

public class Post {
    private String id;
    private String title;
    private String contents;
    private long datetime;
    private String userId;
    private int minPeople;
    private int maxPeople;

    public Post(String id, String title, String contents, long datetime, String userId, int minPeople, int maxPeople) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.datetime = datetime;
        this.userId = userId;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getUserId() {
        return userId;
    }

    public int getMinPeople() {
        return minPeople;
    }

    public int getMaxPeople() {
        return maxPeople;
    }
}
