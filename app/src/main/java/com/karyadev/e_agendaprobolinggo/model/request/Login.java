package com.karyadev.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
