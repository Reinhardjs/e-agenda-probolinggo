package com.karyadev.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class Agenda {

    @SerializedName("kode")
    private String code;

    @SerializedName("limit")
    private String limit;

    @SerializedName("id_user")
    private String idUser;

    @SerializedName("kode_sub")
    private String codeSub;

    public Agenda(String code, String limit, String idUser, String codeSub) {
        this.code = code;
        this.limit = limit;
        this.idUser = idUser;
        this.codeSub = codeSub;
    }
}
