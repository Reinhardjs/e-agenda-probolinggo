package com.example.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("api_code")
    private String apiCode;

    @SerializedName("data")
    private User user;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public String getApiCode() {
        return apiCode;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}