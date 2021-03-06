package com.example.e_agendaprobolinggo.model.request;

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

    @SerializedName("tingkatan")
    private int role;

    public Register(String name, String email, String password, String position, String opd, int created_by, int role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.position = position;
        this.opd = opd;
        this.created_by = created_by;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOpd() {
        return opd;
    }

    public void setOpd(String opd) {
        this.opd = opd;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
