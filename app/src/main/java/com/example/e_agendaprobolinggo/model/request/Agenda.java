package com.example.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class Agenda {

    @SerializedName("kode")
    private String kode;

    @SerializedName("limit")
    private String limit;

    @SerializedName("kode_sub")
    private String kode_sub;

    public Agenda(String kode, String limit, String kode_sub) {
        this.kode = kode;
        this.limit = limit;
        this.kode_sub = kode_sub;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getKode_sub() {
        return kode_sub;
    }

    public void setKode_sub(String kode_sub) {
        this.kode_sub = kode_sub;
    }
}
