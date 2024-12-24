package com.example.mobprogfinal_v1.providers;

import android.util.Log;

import com.example.mobprogfinal_v1.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PostsManager {

    private static final String TAG = "PostsManager";
    private static PostsManager instance;
    private final DatabaseReference postsRef;

    private PostsManager() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        postsRef = database.getReference("posts");
    }

    public static synchronized PostsManager getInstance() {
        if (instance == null) {
            instance = new PostsManager();
        }
        return instance;
    }

    public interface SinglePostCallback {
        void onSuccess(Post post);

        void onError(String error);
    }

    public interface AllPostsCallback {
        void onSuccess(List<Post> posts);

        void onError(String error);
    }

    public void fetchPostById(String postId, SinglePostCallback callback) {
        postsRef.child(postId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DataSnapshot snapshot = task.getResult();
                try {
                    Post post = parsePost(snapshot);
                    if (post != null) {
                        callback.onSuccess(post);
                    } else {
                        callback.onError("Failed to parse post");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing post data: ", e);
                    callback.onError("Failed to parse post data.");
                }
            } else {
                Log.e(TAG, "Error fetching post: ", task.getException());
                callback.onError("Failed to fetch post.");
            }
        });
    }

    public void fetchAllPosts(AllPostsCallback callback) {
        postsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<Post> postList = new ArrayList<>();
                try {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        Post post = parsePost(snapshot);
                        if (post != null) {
                            postList.add(post);
                        } else {
                            Log.e(TAG, "Null post parsed, skipping.");
                        }
                    }
                    callback.onSuccess(postList);
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing posts data: ", e);
                    callback.onError("Failed to parse posts data.");
                }
            } else {
                Log.e(TAG, "Error fetching posts: ", task.getException());
                callback.onError("Failed to fetch posts.");
            }
        });
    }

    public void addPost(Post post, SinglePostCallback callback) {
        if (post == null) {
            callback.onError("Post cannot be null");
            return;
        }

        String newPostId = postsRef.push().getKey();
        if (newPostId == null) {
            callback.onError("Failed to generate post ID");
            return;
        }

        post.setId(newPostId);
        postsRef.child(newPostId).setValue(post.toMap()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(post);
            } else {
                callback.onError("Failed to add post: " + Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }


    private Post parsePost(DataSnapshot snapshot) {
        try {
            String id = snapshot.getKey();
            String title = snapshot.child("title").getValue(String.class);
            String contents = snapshot.child("contents").getValue(String.class);
            Long timestamp = snapshot.child("datetime").getValue(Long.class);
            Integer minPeople = snapshot.child("minPeople").getValue(Integer.class);
            Integer maxPeople = snapshot.child("maxPeople").getValue(Integer.class);
            String userId = snapshot.child("userId").getValue(String.class);

            // Handle null or missing values
            title = title != null ? title : "Untitled";
            contents = contents != null ? contents : "No content";
            minPeople = minPeople != null ? minPeople : 0;
            maxPeople = maxPeople != null ? maxPeople : 0;
            timestamp = timestamp != null ? timestamp : 0L;

            Date datetime = new Date(timestamp);
            return new Post(id, title, contents, datetime, minPeople, maxPeople, userId);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing post snapshot: ", e);
            return null;
        }
    }

    public void deletePost(String postId, ActionCallback callback) {
        postsRef.child(postId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();
            } else {
                callback.onError(task.getException().getMessage());
            }
        });
    }

    public interface ActionCallback {
        void onSuccess();

        void onError(String error);
    }

}
