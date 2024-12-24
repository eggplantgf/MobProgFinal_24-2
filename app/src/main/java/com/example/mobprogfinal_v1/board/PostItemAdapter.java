package com.example.mobprogfinal_v1.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Post;

import java.util.List;

public class PostItemAdapter extends BaseAdapter {

    private final Context context;
    private final List<Post> posts;

    public PostItemAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        }

        Post post = posts.get(position);

        TextView titleTextView = convertView.findViewById(R.id.item_post_title);
        TextView contentTextView = convertView.findViewById(R.id.item_post_content);

        titleTextView.setText(post.getTitle());
        contentTextView.setText(post.getContents());

        return convertView;
    }
}
