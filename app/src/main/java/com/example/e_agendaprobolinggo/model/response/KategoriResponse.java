package com.example.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//import javax.annotation.Generated;

//@Generated("com.robohorse.robopojogenerator")
public class KategoriResponse {

    @SerializedName("data")
    private List<DataKategori> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public void setData(List<DataKategori> data) {
        this.data = data;
    }

    public List<DataKategori> getData() {
        return data;
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
                "KategoriResponse{" +
                        "data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}