package com.example.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class DetailAgenda {

    @SerializedName("kode")
    private String kode;

    @SerializedName("id_user")
    private int idUser;

    public DetailAgenda(String kode, int idUser) {
        this.kode = kode;
        this.idUser = idUser;
    }
}
