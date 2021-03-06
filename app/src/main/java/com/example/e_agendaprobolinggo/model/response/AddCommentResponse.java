package com.example.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

public class AddCommentResponse {

    @SerializedName("data")
    private Object data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}