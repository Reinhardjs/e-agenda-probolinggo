package com.karyadev.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailAgendaResponse {

    @SerializedName("data")
    private List<DataDetailAgenda> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<DataDetailAgenda> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}