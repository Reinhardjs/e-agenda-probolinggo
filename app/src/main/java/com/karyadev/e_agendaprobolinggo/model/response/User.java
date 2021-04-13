package com.karyadev.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("password")
    private String password;

    @SerializedName("tingkatan")
    private String tingkatan;

    @SerializedName("opd")
    private String opd;

    @SerializedName("nama")
    private String nama;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("email")
    private String email;

    @SerializedName("status")
    private String status;

    public String getPassword() {
        return password;
    }

    public String getTingkatan() {
        return tingkatan;
    }

    public String getOpd() {
        return opd;
    }

    public String getNama() {
        return nama;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}