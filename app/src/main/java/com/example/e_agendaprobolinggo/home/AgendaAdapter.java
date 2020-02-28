package com.example.e_agendaprobolinggo.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.DataItem;

import java.util.ArrayList;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    private ArrayList<DataItem> agendas;

    public AgendaAdapter(ArrayList<DataItem> agendas){
        this.agendas = agendas;
    }

    @NonNull
    @Override
    public AgendaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_home_bottom, parent, false);
        return new AgendaAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaAdapter.ViewHolder holder, int position) {
        DataItem agenda = agendas.get(holder.getLayoutPosition());
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
