package com.example.e_agendaprobolinggo.model.body;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("nama")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("opd")
    private String opd;

    @SerializedName("created_by")
    private int created_by;

    public User(String nama, String email, String password, String jabatan, String opd, int created_by) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.jabatan = jabatan;
        this.opd = opd;
        this.created_by = created_by;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getOpd() {
        return opd;
    }

    public void setOpd(String opd) {
        this.opd = opd;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }
}
