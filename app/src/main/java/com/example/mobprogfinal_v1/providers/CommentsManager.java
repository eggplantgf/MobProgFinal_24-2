package com.example.mobprogfinal_v1.providers;

import com.example.mobprogfinal_v1.models.Comment;
import java.util.ArrayList;
import java.util.List;

public class CommentsManager {
    private List<Comment> comments = new ArrayList<>();

    public interface CommentsCallback {
        void onCommentsLoaded(List<Comment> comments);
        void onError(Exception e);
    }

    public interface CommentActionCallback {
        void onComplete(boolean success);
    }

    // 특정 게시글의 댓글 목록 가져오기
    public void fetchComments(String postId, CommentsCallback callback) {
        List<Comment> filteredComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getPostId().equals(postId)) {
                filteredComments.add(comment);
            }
        }
        callback.onCommentsLoaded(filteredComments);
    }

    // 댓글 추가
    public void addComment(String postId, String content, CommentActionCallback callback) {
        Comment newComment = new Comment(String.valueOf(comments.size() + 1), content, System.currentTimeMillis(), postId, "currentUser");
        comments.add(newComment);
        callback.onComplete(true);
    }

    // 댓글 삭제
    public void deleteComment(String commentId, CommentActionCallback callback) {
        comments.removeIf(comment -> comment.getId().equals(commentId));
        callback.onComplete(true);
    }
}
