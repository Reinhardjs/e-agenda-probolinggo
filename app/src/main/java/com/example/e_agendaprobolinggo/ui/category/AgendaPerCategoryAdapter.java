package com.example.e_agendaprobolinggo.ui.category;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.ui.detail.DetailActivity;

import java.util.ArrayList;

public class AgendaPerCategoryAdapter extends RecyclerView.Adapter<AgendaPerCategoryAdapter.ViewHolder> {

    private ArrayList<DataAgenda> agendas;

    public AgendaPerCategoryAdapter(ArrayList<DataAgenda> agendas){
        this.agendas = agendas;
    }

    @NonNull
    @Override
    public AgendaPerCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_home_bottom, parent, false);

        AgendaPerCategoryAdapter.ViewHolder viewHolder = new AgendaPerCategoryAdapter.ViewHolder(rootView);

        rootView.setOnClickListener(v -> {
            Intent detailIntent = new Intent(parent.getContext(), DetailActivity.class);
            detailIntent.putExtra(DetailActivity.KEY, agendas.get(viewHolder.getAdapterPosition()).getIdEncode());
            parent.getContext().startActivity(detailIntent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaPerCategoryAdapter.ViewHolder holder, int position) {
        DataAgenda agenda = agendas.get(holder.getLayoutPosition());
        holder.tvTitle.setText(agenda.getNamaKegiatan());
        holder.tvSubtitle1.setText(agenda.getAgenda());
        holder.tvSubtitle2.setText(agenda.getKategori());
        holder.tvCreatedAt.setText(agenda.getCreatedAt());
        holder.tvLabeled.setText(agenda.getStatusKehadiran());
        holder.tvTime.setText(agenda.getJam() + " - " + agenda.getJamend());
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvSubtitle1, tvSubtitle2, tvCreatedAt, tvLabeled, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle1 = itemView.findViewById(R.id.tvSubtitle1);
            tvSubtitle2 = itemView.findViewById(R.id.tvSubtitle2);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvLabeled = itemView.findViewById(R.id.tvLabeled);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

}
