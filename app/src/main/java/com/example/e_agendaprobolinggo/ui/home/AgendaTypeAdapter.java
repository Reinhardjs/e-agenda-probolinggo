package com.example.e_agendaprobolinggo.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.body.AgendaType;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AgendaTypeAdapter extends RecyclerView.Adapter<AgendaTypeAdapter.ViewHolder> {

    private ArrayList<AgendaType> agendaTypes;
    private OnClickAgendaTypeCallback onClickAgendaTypeCallback;

    public AgendaTypeAdapter(ArrayList<AgendaType> agendaTypes) {
        this.agendaTypes = agendaTypes;
    }

    public void setOnClickAgendaTypeCallback(OnClickAgendaTypeCallback callback) {
        onClickAgendaTypeCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_home_category, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AgendaType agendaType = agendaTypes.get(holder.getLayoutPosition());

        holder.tvCategory.setText(agendaType.getAgendaName());
        holder.container.setOnClickListener(view -> {
            onClickAgendaTypeCallback.onClickAgendaType(agendaType);
        });
    }

    @Override
    public int getItemCount() {
        return agendaTypes.size();
    }

    interface OnClickAgendaTypeCallback {
        void onClickAgendaType(AgendaType agendaType);
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
