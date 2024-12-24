package com.example.mobprogfinal_v1.board;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.providers.AuthManager;
import com.example.mobprogfinal_v1.providers.PostsManager;

import java.util.Date;

public class PostFormActivity extends AppCompatActivity {

    private boolean isEditMode;
    private EditText titleInput, contentInput;
    private TextView titleLabel, contentLabel;
    private Button savePostButton;
    private ImageButton editButton, deleteButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);

        isEditMode = getIntent().getBooleanExtra("isEditMode", false);

        initViews();

        if (!isEditMode) {
            loadPostDetails();
        } else {
            setupSaveButton();
        }
    }

    private void initViews() {
        titleInput = findViewById(R.id.title_input);
        contentInput = findViewById(R.id.content_input);
        titleLabel = findViewById(R.id.post_title_label);
        contentLabel = findViewById(R.id.post_content_label);
        savePostButton = findViewById(R.id.save_post_button);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);
        backButton = findViewById(R.id.back_button); // 뒤로가기 버튼 추가

        if (isEditMode) {
            titleLabel.setVisibility(View.GONE);
            contentLabel.setVisibility(View.GONE);
        } else {
            titleInput.setVisibility(View.GONE);
            contentInput.setVisibility(View.GONE);
            savePostButton.setVisibility(View.GONE);
        }

        // 뒤로가기 버튼 기능 추가
        backButton.setOnClickListener(v -> finish());
    }

    private void setupSaveButton() {
        savePostButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = AuthManager.getInstance().getCurrentUserId();
            if (userId == null) {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
                return;
            }

            Post post = new Post(null, title, content, new Date(), 0, 0, userId);
            PostsManager.getInstance().addPost(post, new PostsManager.SinglePostCallback() {
                @Override
                public void onSuccess(Post newPost) {
                    Toast.makeText(PostFormActivity.this, "Post created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(PostFormActivity.this, "Failed to create post: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadPostDetails() {
        String postId = getIntent().getStringExtra("postId");
        if (postId == null) {
            Toast.makeText(this, "Post ID not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        PostsManager.getInstance().fetchPostById(postId, new PostsManager.SinglePostCallback() {
            @Override
            public void onSuccess(Post post) {
                titleLabel.setText(post.getTitle());
                contentLabel.setText(post.getContents());
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PostFormActivity.this, "Failed to load post: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
