package com.karyadev.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("data")
    private DataRegister dataRegister;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public void setDataRegister(DataRegister dataRegister) {
        this.dataRegister = dataRegister;
    }

    public DataRegister getDataRegister() {
        return dataRegister;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "RegisterResponse{" +
                        "data = '" + dataRegister + '\'' +
                        ",message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}