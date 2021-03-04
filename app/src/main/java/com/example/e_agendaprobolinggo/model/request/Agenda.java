package com.example.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class Agenda {

    @SerializedName("kode")
    private String kode;

    @SerializedName("limit")
    private String limit;

    @SerializedName("id_user")
    private String idUser;

    @SerializedName("kode_sub")
    private String kodeSub;

    public Agenda(String kode, String limit, String idUser, String kodeSub) {
        this.kode = kode;
        this.limit = limit;
        this.idUser = idUser;
        this.kodeSub = kodeSub;
    }
}
