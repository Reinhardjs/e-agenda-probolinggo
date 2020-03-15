package com.example.e_agendaprobolinggo.ui.category;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.ui.detail.DetailActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AgendaPerCategoryAdapter extends RecyclerView.Adapter<AgendaPerCategoryAdapter.ViewHolder> {

    private ArrayList<DataAgenda> agendas;
    private Context context;

    public AgendaPerCategoryAdapter(ArrayList<DataAgenda> agendas, Context context){
        this.agendas = agendas;
        this.context = context;
    }

    @NonNull
    @Override
    public AgendaPerCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_home_agenda, parent, false);

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
        holder.tvDate.setText(agenda.getTanggal());
        holder.tvLabeled.setText(agenda.getStatusAgenda());
        holder.tvClothes.setText(agenda.getPakaian());
        holder.tvPlace.setText(agenda.getTempat());
        holder.cardLabeled.setCardBackgroundColor(Color.parseColor(agenda.getStatusColor()));
        holder.tvTime.setText(agenda.getJam());
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvSubtitle1, tvSubtitle2, tvDate, tvLabeled, tvTime, tvPlace, tvClothes;
        public MaterialCardView cardLabeled;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle1 = itemView.findViewById(R.id.tvSubtitle1);
            tvSubtitle2 = itemView.findViewById(R.id.tvSubtitle2);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLabeled = itemView.findViewById(R.id.tvLabeled);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            tvClothes = itemView.findViewById(R.id.tvClothes);
            cardLabeled = itemView.findViewById(R.id.cardLabeled);
        }
    }

}
