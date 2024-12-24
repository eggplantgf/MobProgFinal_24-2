package com.example.mobprogfinal_v1.board;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.adapters.CommentItemAdapter;
import com.example.mobprogfinal_v1.models.Comment;
import com.example.mobprogfinal_v1.providers.AuthManager;
import com.example.mobprogfinal_v1.providers.CommentsManager;
import com.example.mobprogfinal_v1.providers.PostsManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {

    private TextView titleView, contentView, userView;
    private EditText commentInput;
    private ImageButton backButton, editButton, deleteButton, sendCommentButton;
    private RecyclerView commentsRecyclerView;
    private CommentItemAdapter commentItemAdapter;
    private List<Comment> comments;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        postId = getIntent().getStringExtra("postId");
        if (postId == null || postId.isEmpty()) {
            Toast.makeText(this, "Invalid post ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        loadPostDetails();
        loadComments();
        setupListeners();
    }

    private void initViews() {
        titleView = findViewById(R.id.post_title);
        contentView = findViewById(R.id.post_content);
        userView = findViewById(R.id.post_user);
        commentInput = findViewById(R.id.comment_input);
        backButton = findViewById(R.id.back_button);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);
        sendCommentButton = findViewById(R.id.send_comment_button);
        commentsRecyclerView = findViewById(R.id.comments_list);

        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        comments = new ArrayList<>();
        commentItemAdapter = new CommentItemAdapter(this, comments);
        commentsRecyclerView.setAdapter(commentItemAdapter);

        // 기본적으로 버튼을 숨김
        editButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
    }

    private void setupListeners() {
        backButton.setOnClickListener(v -> finish());

        editButton.setOnClickListener(v -> {
            Toast.makeText(this, "Edit Post feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        deleteButton.setOnClickListener(v -> {
            PostsManager.getInstance().deletePost(postId, new PostsManager.ActionCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(PostDetailActivity.this, "Post deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(PostDetailActivity.this, "Failed to delete post: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        sendCommentButton.setOnClickListener(v -> {
            String content = commentInput.getText().toString().trim();
            if (content.isEmpty()) {
                Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = AuthManager.getInstance().getCurrentUserId();
            if (userId == null) {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
                return;
            }

            Comment comment = new Comment(null, postId, userId, content, new Date());
            CommentsManager.getInstance().addComment(postId, comment, new CommentsManager.CompletionCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(PostDetailActivity.this, "Comment added successfully", Toast.LENGTH_SHORT).show();
                    commentInput.setText("");
                    loadComments();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(PostDetailActivity.this, "Failed to add comment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadPostDetails() {
        PostsManager.getInstance().fetchPostById(postId, new PostsManager.SinglePostCallback() {
            @Override
            public void onSuccess(Post post) {
                titleView.setText(post.getTitle());
                contentView.setText(post.getContents());
                userView.setText(post.getUserId() != null ? post.getUserId() : "Unknown User");

                String currentUserId = AuthManager.getInstance().getCurrentUserId();
                if (post.getUserId() != null && post.getUserId().equals(currentUserId)) {
                    editButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PostDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadComments() {
        CommentsManager.getInstance().fetchComments(postId, new CommentsManager.CommentsCallback() {
            @Override
            public void onSuccess(List<Comment> fetchedComments) {
                comments.clear();
                comments.addAll(fetchedComments);
                commentItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(PostDetailActivity.this, "Failed to load comments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
