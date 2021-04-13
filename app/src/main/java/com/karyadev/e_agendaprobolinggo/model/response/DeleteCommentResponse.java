package com.karyadev.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

public class DeleteCommentResponse {

    @SerializedName("data")
    private String data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}