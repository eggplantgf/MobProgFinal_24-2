package com.example.mobprogfinal_v1.providers;

import com.example.mobprogfinal_v1.models.Post;
import java.util.ArrayList;
import java.util.List;

public class PostsManager {
    private List<Post> posts = new ArrayList<>();

    public interface PostsCallback {
        void onPostsLoaded(List<Post> posts);
        void onPostLoaded(Post post);
        void onError(Exception e);
    }

    public interface PostActionCallback {
        void onComplete(boolean success);
    }

    // 게시글 목록 가져오기
    public void fetchPosts(PostsCallback callback) {
        // 테스트 데이터 (Firebase 연동 시 교체 필요)
        posts.add(new Post("1", "Sample Title", "Sample Content", System.currentTimeMillis(), "user123", 2, 5));
        callback.onPostsLoaded(posts);
    }

    // 특정 게시글 가져오기
    public void getPost(String postId, PostsCallback callback) {
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                callback.onPostLoaded(post);
                return;
            }
        }
        callback.onError(new Exception("Post not found"));
    }

    // 게시글 추가
    public void addPost(Post post, PostActionCallback callback) {
        post.setId(String.valueOf(posts.size() + 1)); // ID 생성 (Firebase 연동 시 교체)
        posts.add(post);
        callback.onComplete(true);
    }

    // 게시글 업데이트
    public void updatePost(String postId, Post updatedPost, PostActionCallback callback) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(postId)) {
                posts.set(i, updatedPost);
                callback.onComplete(true);
                return;
            }
        }
        callback.onComplete(false);
    }

    // 게시글 삭제
    public void deletePost(String postId, PostActionCallback callback) {
        posts.removeIf(post -> post.getId().equals(postId));
        callback.onComplete(true);
    }
}
