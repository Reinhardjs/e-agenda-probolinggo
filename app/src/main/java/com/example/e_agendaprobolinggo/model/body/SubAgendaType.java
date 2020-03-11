package com.example.e_agendaprobolinggo.model.body;

import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubAgendaType {

    @SerializedName("id_sub_agenda")
    private String idSubAgenda;

    @SerializedName("sub_agenda_name")
    private String subAgendaName;

    @SerializedName("agenda_list")
    private ArrayList<DataAgenda> agendaList;

    public SubAgendaType(String id, String subAgendaName){
        this.idSubAgenda = id;
        this.subAgendaName = subAgendaName;
        this.agendaList = new ArrayList<>();
    }

    public String getIdSubAgenda() {
        return idSubAgenda;
    }

    public void setIdSubAgenda(String idSubAgenda) {
        this.idSubAgenda = idSubAgenda;
    }

    public String getSubAgendaName() {
        return subAgendaName;
    }

    public void setSubAgendaName(String subAgendaName) {
        this.subAgendaName = subAgendaName;
    }

    public ArrayList<DataAgenda> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(ArrayList<DataAgenda> agendaList) {
        this.agendaList = agendaList;
    }
}
