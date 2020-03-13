package com.example.e_agendaprobolinggo.ui.home;

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

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    private ArrayList<DataAgenda> agendas;

    public AgendaAdapter(ArrayList<DataAgenda> agendas){
        this.agendas = agendas;
    }

    public void replaceList(ArrayList<DataAgenda> list){
        agendas = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AgendaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_home_agenda, parent, false);

        AgendaAdapter.ViewHolder viewHolder = new AgendaAdapter.ViewHolder(rootView);

        rootView.setOnClickListener(v -> {
            Intent detailIntent = new Intent(parent.getContext(), DetailActivity.class);
            detailIntent.putExtra(DetailActivity.KEY, agendas.get(viewHolder.getAdapterPosition()).getIdEncode());
            parent.getContext().startActivity(detailIntent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaAdapter.ViewHolder holder, int position) {
        DataAgenda agenda = agendas.get(holder.getLayoutPosition());
        holder.tvTitle.setText(agenda.getNamaKegiatan());
        holder.tvSubtitle1.setText(agenda.getAgenda());
        holder.tvSubtitle2.setText(agenda.getKategori());
        holder.tvDate.setText(agenda.getTanggal());
        holder.tvLabeled.setText(Integer.parseInt(agenda.getStatusKehadiran()) != 0 ? "Hadir" : "Tidak Hadir");
        holder.tvTime.setText(agenda.getJam());
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvSubtitle1, tvSubtitle2, tvDate, tvLabeled, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle1 = itemView.findViewById(R.id.tvSubtitle1);
            tvSubtitle2 = itemView.findViewById(R.id.tvSubtitle2);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLabeled = itemView.findViewById(R.id.tvLabeled);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

}
