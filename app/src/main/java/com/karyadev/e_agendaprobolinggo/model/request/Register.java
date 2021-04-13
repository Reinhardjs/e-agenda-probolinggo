package com.karyadev.e_agendaprobolinggo.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Register implements Serializable {

    @SerializedName("nama")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("jabatan")
    private String position;

    @SerializedName("opd")
    private String opd;

    @SerializedName("created_by")
    private int created_by;

    public Register(String name, String email, String password, String position, String opd, int created_by) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.position = position;
        this.opd = opd;
        this.created_by = created_by;
    }

}
