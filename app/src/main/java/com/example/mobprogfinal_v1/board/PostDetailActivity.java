package com.example.mobprogfinal_v1.board;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.providers.PostsManager;

public class PostDetailActivity extends AppCompatActivity {

    private TextView titleView, contentView, userView;
    private ImageButton backButton;
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
    }

    private void initViews() {
        titleView = findViewById(R.id.post_title);
        contentView = findViewById(R.id.post_content);
        userView = findViewById(R.id.post_user);
        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> finish());
    }

    private void loadPostDetails() {
        PostsManager.getInstance().fetchPostById(postId, new PostsManager.SinglePostCallback() {
            @Override
            public void onSuccess(Post post) {
                titleView.setText(post.getTitle());
                contentView.setText(post.getContents());
                userView.setText(post.getUserId() != null ? post.getUserId() : "Unknown User");
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PostDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
