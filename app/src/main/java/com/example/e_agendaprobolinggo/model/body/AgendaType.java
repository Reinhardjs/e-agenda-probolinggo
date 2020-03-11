package com.example.e_agendaprobolinggo.model.body;

import android.util.SparseArray;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AgendaType implements Serializable {

    @SerializedName("id_agenda")
    private String idAgenda;

    @SerializedName("agenda_name")
    private String agendaName;

    @SerializedName("sub_agenda_list")
    private SparseArray<SubAgendaType> subAgendaList;

    public String getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(String idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getAgendaName() {
        return agendaName;
    }

    public void setAgendaName(String agendaName) {
        this.agendaName = agendaName;
    }

    public SparseArray<SubAgendaType> getSubAgendaList() {
        return subAgendaList;
    }

    public void setSubAgendaList(SparseArray<SubAgendaType> agendaList) {
        this.subAgendaList = agendaList;
    }
}
