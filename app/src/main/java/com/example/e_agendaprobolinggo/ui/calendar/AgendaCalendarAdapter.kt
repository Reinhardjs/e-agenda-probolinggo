package com.example.e_agendaprobolinggo.ui.calendar

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_agendaprobolinggo.R
import com.example.e_agendaprobolinggo.databinding.ItemHomeAgendaBinding
import com.example.e_agendaprobolinggo.model.response.DataItem

class AgendaCalendarAdapter : RecyclerView.Adapter<AgendaCalendarAdapter.ViewHolder>() {

    val agendas = mutableListOf<DataItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomeAgendaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(agendas[position])
    }

    override fun getItemCount(): Int = agendas.size

    inner class ViewHolder(val binding: ItemHomeAgendaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(agenda: DataItem) {
            binding.tvTitle.text = agenda.namaKegiatan
            binding.tvSubtitle1.text = agenda.subAgenda
            binding.tvSubtitle2.text = agenda.kategori
            binding.tvDate.text = agenda.tanggal
            binding.tvLabeled.text = agenda.statusAgenda
            binding.tvClothes.text = agenda.pakaian
            binding.tvPlace.text = agenda.tempat
            binding.cardLabeled.setCardBackgroundColor(Color.parseColor(agenda.statusColor))

            val ld = ContextCompat.getDrawable(binding.root.context, R.drawable.item_left_border) as LayerDrawable
            val leftBorder = ld.findDrawableByLayerId(R.id.left_border) as GradientDrawable
            leftBorder.setColor(Color.parseColor(agenda.statusBox))

            binding.container.background = ld
        }
    }
}