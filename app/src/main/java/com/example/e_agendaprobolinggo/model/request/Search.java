package com.example.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class Search {

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("kode")
    private String kode;

    @SerializedName("kode_sub")
    private String kodeSub;

    public Search(String keyword, String kode, String kodeSub) {
        this.keyword = keyword;
        this.kode = kode;
        this.kodeSub = kodeSub;
    }
}
