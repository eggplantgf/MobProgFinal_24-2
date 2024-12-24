package com.example.mobprogfinal_v1.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {

    private String id;
    private String title;
    private String contents;
    private Date datetime;
    private int minPeople;
    private int maxPeople;
    private String userId;

    public Post(String id, String title, String contents, Date datetime, int minPeople, int maxPeople, String userId) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.datetime = datetime;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
        this.userId = userId;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Date getDatetime() {
        return datetime;
    }

    public int getMinPeople() {
        return minPeople;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("title", title);
        postMap.put("contents", contents);
        postMap.put("datetime", datetime != null ? datetime.getTime() : null);
        postMap.put("minPeople", minPeople);
        postMap.put("maxPeople", maxPeople);
        postMap.put("userId", userId);
        return postMap;
    }
}
