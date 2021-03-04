package com.example.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

public class ListKomentarAgenda {

    @SerializedName("btn_hapus_komentar")
    private int btnHapusKomentar;

    @SerializedName("updated_at")
    private Object updatedAt;

    @SerializedName("nama")
    private String nama;

    @SerializedName("foto")
    private String foto;

    @SerializedName("komentar")
    private String komentar;

    @SerializedName("id_agenda")
    private String idAgenda;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    @SerializedName("id_user")
    private String idUser;

    @SerializedName("created_by")
    private Object createdBy;

    @SerializedName("status")
    private Object status;

    public int getBtnHapusKomentar() {
        return btnHapusKomentar;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public String getNama() {
        return nama;
    }

    public String getFoto() {
        return foto;
    }

    public String getKomentar() {
        return komentar;
    }

    public String getIdAgenda() {
        return idAgenda;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getIdUser() {
        return idUser;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public Object getStatus() {
        return status;
    }
}