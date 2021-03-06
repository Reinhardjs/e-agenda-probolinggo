package com.example.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class Search {

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("kode")
    private String code;

    @SerializedName("kode_sub")
    private String codeSub;

    public Search(String keyword, String code, String codeSub) {
        this.keyword = keyword;
        this.code = code;
        this.codeSub = codeSub;
    }
}
