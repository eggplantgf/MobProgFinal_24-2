package com.example.mobprogfinal_v1.providers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobprogfinal_v1.models.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentsManager {

    private static CommentsManager instance;
    private final DatabaseReference commentsRef;

    private CommentsManager() {
        commentsRef = FirebaseDatabase.getInstance().getReference("comments");
    }

    public static CommentsManager getInstance() {
        if (instance == null) {
            instance = new CommentsManager();
        }
        return instance;
    }

    public void fetchComments(String postId, CommentsCallback callback) {
        commentsRef.child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Comment> comments = new ArrayList<>();
                for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    if (comment != null) {
                        comments.add(comment);
                    }
                }
                callback.onSuccess(comments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.toException());
            }
        });
    }

    public void addComment(String postId, Comment comment, CompletionCallback callback) {
        String key = commentsRef.child(postId).push().getKey();
        if (key != null) {
            comment.setId(key);
            commentsRef.child(postId).child(key).setValue(comment)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            callback.onSuccess();
                        } else {
                            callback.onFailure(task.getException());
                        }
                    });
        } else {
            callback.onFailure(new Exception("Failed to generate comment ID"));
        }
    }

    public void deleteComment(String postId, String commentId, CompletionCallback callback) {
        commentsRef.child(postId).child(commentId).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public interface CommentsCallback {
        void onSuccess(List<Comment> comments);

        void onFailure(Exception e);
    }

    public interface CompletionCallback {
        void onSuccess();

        void onFailure(Exception e);
    }
}
