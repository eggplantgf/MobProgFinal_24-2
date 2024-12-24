package com.example.mobprogfinal_v1.board;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.adapters.PostsAdapter;
import com.example.mobprogfinal_v1.models.Post;
import com.example.mobprogfinal_v1.providers.PostsManager;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView postsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PostsAdapter postsAdapter;
    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        initViews();
        setupRecyclerView();
        setupListeners();
        loadPosts();
    }

    private void initViews() {
        postsRecyclerView = findViewById(R.id.posts_list_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadPosts();
            swipeRefreshLayout.setRefreshing(false);
        });

        // 메뉴 버튼 클릭 시 Toast 출력
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v ->
                Toast.makeText(this, "드로우바 기능 미구현", Toast.LENGTH_SHORT).show());

        // 검색 버튼 클릭 시 Toast 출력
        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v ->
                Toast.makeText(this, "검색 기능 미구현", Toast.LENGTH_SHORT).show());
    }

    private void setupRecyclerView() {
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        posts = new ArrayList<>();
        postsAdapter = new PostsAdapter(this, posts);
        postsRecyclerView.setAdapter(postsAdapter);
    }

    private void setupListeners() {
        findViewById(R.id.write_post_button).setOnClickListener(v -> {
            Intent intent = new Intent(BoardActivity.this, PostFormActivity.class);
            intent.putExtra("isEditMode", true);
            startActivity(intent);
        });
    }

    private void loadPosts() {
        Toast.makeText(this, "Loading posts...", Toast.LENGTH_SHORT).show();

        PostsManager.getInstance().fetchAllPosts(new PostsManager.AllPostsCallback() {
            @Override
            public void onSuccess(List<Post> fetchedPosts) {
                if (fetchedPosts.isEmpty()) {
                    Toast.makeText(BoardActivity.this, "No posts available.", Toast.LENGTH_SHORT).show();
                } else {
                    posts.clear();
                    posts.addAll(fetchedPosts);
                    postsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(BoardActivity.this, "Failed to load posts: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
