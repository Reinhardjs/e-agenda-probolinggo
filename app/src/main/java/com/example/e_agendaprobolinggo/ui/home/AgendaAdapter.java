package com.example.e_agendaprobolinggo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.ui.detail.DetailActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    private ArrayList<DataAgenda> agendas;
    private Context context;

    public AgendaAdapter(ArrayList<DataAgenda> agendas, Context context){
        this.agendas = agendas;
        this.context = context;
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
        holder.tvSubtitle1.setText(agenda.getSubAgenda());
        holder.tvSubtitle2.setText(agenda.getKategori());
        holder.tvDate.setText(agenda.getTanggal());
        holder.tvLabeled.setText(agenda.getStatusAgenda());
        holder.tvClothes.setText(agenda.getPakaian());
        holder.tvPlace.setText(agenda.getTempat());
        holder.cardLabeled.setCardBackgroundColor(Color.parseColor(agenda.getStatusColor()));
        holder.tvTime.setText(agenda.getJam());

        // https://stackoverflow.com/questions/32163918/programmatically-change-color-of-shape-in-layer-list
        LayerDrawable ld = (LayerDrawable) context.getResources().getDrawable(R.drawable.item_left_border);
        GradientDrawable leftBorder = (GradientDrawable)ld.findDrawableByLayerId(R.id.left_border);
        leftBorder.setColor(Color.parseColor(agenda.getStatusBox()));

        holder.container.setBackground(ld);
    }

    @Override
    public int getItemCount() {
        return agendas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvSubtitle1, tvSubtitle2, tvDate, tvLabeled, tvTime, tvPlace, tvClothes;
        public MaterialCardView cardLabeled;
        public ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
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
