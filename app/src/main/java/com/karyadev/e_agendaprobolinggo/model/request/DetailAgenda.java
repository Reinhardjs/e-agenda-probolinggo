package com.karyadev.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class DetailAgenda {

    @SerializedName("kode")
    private String code;

    @SerializedName("id_user")
    private String idUser;

    public DetailAgenda(String code, String idUser) {
        this.code = code;
        this.idUser = idUser;
    }
}
