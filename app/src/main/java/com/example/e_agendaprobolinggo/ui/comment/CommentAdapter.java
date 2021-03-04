package com.example.e_agendaprobolinggo.ui.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.ListKomentarAgenda;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final ArrayList<ListKomentarAgenda> comments = new ArrayList<>();

    public void setData(List<ListKomentarAgenda> newListData) {
        if (newListData == null) return;
        comments.clear();
        comments.addAll(newListData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_comment, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListKomentarAgenda comment = comments.get(holder.getLayoutPosition());

        Glide.with(holder.itemView.getContext()).load(comment.getFoto()).apply(
                RequestOptions.placeholderOf(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
        ).into(holder.imgProfileComment);
        holder.tvDateComment.setText(comment.getCreatedAt());
        holder.tvNameComment.setText(comment.getNama());
        holder.tvDraftComment.setText(comment.getKomentar());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imgProfileComment;
        public TextView tvDateComment, tvNameComment, tvDraftComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfileComment = itemView.findViewById(R.id.img_profile_comment);
            tvDateComment = itemView.findViewById(R.id.tv_date_comment);
            tvNameComment = itemView.findViewById(R.id.tv_name_comment);
            tvDraftComment = itemView.findViewById(R.id.tv_draft_comment);
        }
    }
}
