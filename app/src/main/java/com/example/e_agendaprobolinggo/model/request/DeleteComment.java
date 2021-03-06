package com.example.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class DeleteComment {

    @SerializedName("id_komentar")
    private String idComment;

    @SerializedName("id_user")
    private String idUser;

    public DeleteComment(String idComment, String idUser) {
        this.idComment = idComment;
        this.idUser = idUser;
    }
}
