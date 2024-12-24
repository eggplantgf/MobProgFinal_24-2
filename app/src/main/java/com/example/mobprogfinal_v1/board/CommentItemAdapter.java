package com.example.mobprogfinal_v1.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Comment;

import java.util.List;

public class CommentItemAdapter extends BaseAdapter {

    private final Context context;
    private final List<Comment> comments;

    public CommentItemAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        }

        Comment comment = comments.get(position);

        TextView contentTextView = convertView.findViewById(R.id.item_comment_content);

        contentTextView.setText(comment.getContent());

        return convertView;
    }
}
