package com.example.mobprogfinal_v1.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobprogfinal_v1.R;
import com.example.mobprogfinal_v1.models.Comment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.CommentViewHolder> {
    private List<Comment> comments;

    public CommentItemAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false); // item_comment.xml 참조
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.commentText.setText(comment.getContents()); // 댓글 내용 설정
        holder.dateText.setText(formatDate(comment.getDatetime())); // 날짜 포맷 설정
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    // 날짜 포맷 설정
    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(timestamp);
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentText, dateText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.comment_text); // item_comment.xml의 ID 참조
            dateText = itemView.findViewById(R.id.comment_date); // item_comment.xml의 ID 참조
        }
    }
}
