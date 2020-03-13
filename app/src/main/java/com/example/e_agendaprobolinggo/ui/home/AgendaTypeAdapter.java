package com.example.e_agendaprobolinggo.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.DataKategori;
import com.example.e_agendaprobolinggo.model.response.DataSubKategori;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class AgendaTypeAdapter extends RecyclerView.Adapter<AgendaTypeAdapter.ViewHolder> {

    private ArrayList<DataKategori> agendaTypes;
    private OnClickAgendaTypeCallback onClickAgendaTypeCallback;
    private Context context;

    public AgendaTypeAdapter(ArrayList<DataKategori> agendaTypes) {
        this.agendaTypes = agendaTypes;
    }

    public void setOnClickAgendaTypeCallback(OnClickAgendaTypeCallback callback) {
        onClickAgendaTypeCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_home_agendatype, parent, false);
        context = parent.getContext();
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataKategori dataKategori = agendaTypes.get(holder.getLayoutPosition());

        holder.tvCategory.setText(dataKategori.getRole());
//        Glide.with(context).load(dataKategori.getRoleImg()).into(holder.imgCategory);

        holder.container.setOnClickListener(view -> {
            onClickAgendaTypeCallback.onClickAgendaType(dataKategori.getMasterSubRole());
        });
    }

    @Override
    public int getItemCount() {
        return agendaTypes.size();
    }

    interface OnClickAgendaTypeCallback {
        void onClickAgendaType(List<DataSubKategori> agendaType);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView container;
        TextView tvCategory;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }

}
