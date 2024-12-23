package com.example.mobprogfinal_v1.board;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.providers.PostsManager;

public class EditPostActivity extends AppCompatActivity {
    private EditText titleInput, contentInput, minPeopleInput, maxPeopleInput;
    private Button savePostButton;
    private PostsManager postsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        titleInput = findViewById(R.id.title_input);
        contentInput = findViewById(R.id.content_input);
        minPeopleInput = findViewById(R.id.min_people_input);
        maxPeopleInput = findViewById(R.id.max_people_input);
        savePostButton = findViewById(R.id.save_post_button);

        postsManager = new PostsManager();

        savePostButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();
            int minPeople = Integer.parseInt(minPeopleInput.getText().toString());
            int maxPeople = Integer.parseInt(maxPeopleInput.getText().toString());

            Post newPost = new Post(null, title, content, System.currentTimeMillis(), "currentUser", minPeople, maxPeople);

            postsManager.addPost(newPost, success -> {
                if (success) {
                    Toast.makeText(EditPostActivity.this, "Post saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditPostActivity.this, "Failed to save post", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
