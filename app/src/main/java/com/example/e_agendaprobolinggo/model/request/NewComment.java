package com.example.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class NewComment {

    @SerializedName("kode")
    private String code;

    @SerializedName("id_user")
    private String idUser;

    @SerializedName("komentar")
    private String comment;

    public NewComment(String code, String idUser, String comment) {
        this.code = code;
        this.idUser = idUser;
        this.comment = comment;
    }
}
