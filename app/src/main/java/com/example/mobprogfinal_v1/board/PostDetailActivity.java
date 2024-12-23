package com.example.mobprogfinal_v1.board;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Comment;
import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.providers.CommentsManager;
import com.example.mobprogfinal_v1.providers.PostsManager;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private TextView postTitleTextView, postContentTextView;
    private RecyclerView commentsRecyclerView;
    private CommentItemAdapter commentItemAdapter;
    private List<Comment> commentList = new ArrayList<>();
    private PostsManager postsManager;
    private CommentsManager commentsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        postTitleTextView = findViewById(R.id.post_title);
        postContentTextView = findViewById(R.id.post_content);
        commentsRecyclerView = findViewById(R.id.comments_recycler_view);

        postsManager = new PostsManager();
        commentsManager = new CommentsManager();

        commentItemAdapter = new CommentItemAdapter(commentList);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(commentItemAdapter);

        String postId = getIntent().getStringExtra("postId");
        if (postId != null) {
            loadPostDetails(postId);
            loadComments(postId);
        }
    }

    private void loadPostDetails(String postId) {
        postsManager.getPost(postId, new PostsManager.PostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> posts) {
                // 사용되지 않음 (목록 로드는 필요하지 않음)
            }

            @Override
            public void onPostLoaded(Post post) {
                postTitleTextView.setText(post.getTitle());
                postContentTextView.setText(post.getContents());
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(PostDetailActivity.this, "Failed to load post details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComments(String postId) {
        commentsManager.fetchComments(postId, new CommentsManager.CommentsCallback() {
            @Override
            public void onCommentsLoaded(List<Comment> comments) {
                commentList.clear();
                commentList.addAll(comments);
                commentItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(PostDetailActivity.this, "Failed to load comments", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
