package com.example.e_agendaprobolinggo.ui.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.databinding.ItemCommentBinding;
import com.example.e_agendaprobolinggo.model.response.ListKomentarAgenda;

import java.util.ArrayList;
import java.util.List;

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
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemCommentBinding binding = ItemCommentBinding.bind(itemView);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(ListKomentarAgenda comment) {
            Glide.with(itemView.getContext()).load(comment.getFoto()).apply(
                    RequestOptions.placeholderOf(R.drawable.ic_profile)
                            .error(R.drawable.ic_profile)
            ).into(binding.imgProfileComment);
            binding.tvDateComment.setText(comment.getCreatedAt());
            binding.tvNameComment.setText(comment.getNama());
            binding.tvDraftComment.setText(comment.getKomentar());
        }
    }
}
