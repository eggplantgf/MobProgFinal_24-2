package com.example.mobprogfinal_v1.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.providers.PostsManager;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private RecyclerView postsRecyclerView;
    private PostItemAdapter postItemAdapter;
    private List<Post> postList = new ArrayList<>();
    private PostsManager postsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        postsRecyclerView = findViewById(R.id.posts_recycler_view);
        Button addPostButton = findViewById(R.id.add_post_button);

        postsManager = new PostsManager();

        postItemAdapter = new PostItemAdapter(postList, post -> {
            // 게시글 클릭 시 PostDetailActivity로 이동
            Intent intent = new Intent(BoardActivity.this, PostDetailActivity.class);
            intent.putExtra("postId", post.getId());
            startActivity(intent);
        });

        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsRecyclerView.setAdapter(postItemAdapter);

        // 게시글 로드
        loadPosts();

        addPostButton.setOnClickListener(v -> {
            // 게시글 추가 화면으로 이동
            Intent intent = new Intent(BoardActivity.this, EditPostActivity.class);
            startActivity(intent);
        });
    }

    private void loadPosts() {
        postsManager.fetchPosts(new PostsManager.PostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> posts) {
                postList.clear();
                postList.addAll(posts);
                postItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostLoaded(Post post) {
                // 사용되지 않음 (개별 게시글 로드는 필요하지 않음)
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(BoardActivity.this, "Failed to load posts", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
