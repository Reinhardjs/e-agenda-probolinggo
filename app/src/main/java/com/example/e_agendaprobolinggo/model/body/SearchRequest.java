package com.example.e_agendaprobolinggo.model.body;

import com.google.gson.annotations.SerializedName;

public class SearchRequest {

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("kode")
    private String kode;

    @SerializedName("kode_sub")
    private String kode_sub;

    public SearchRequest(String keyword, String kode, String kode_sub) {
        this.keyword = keyword;
        this.kode = kode;
        this.kode_sub = kode_sub;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKode_sub() {
        return kode_sub;
    }

    public void setKode_sub(String kode_sub) {
        this.kode_sub = kode_sub;
    }
}
