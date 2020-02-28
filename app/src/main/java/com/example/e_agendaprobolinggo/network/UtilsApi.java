package com.example.e_agendaprobolinggo.network;

public class UtilsApi {

    public static NetworkApi getApiService(){
        return NetworkClient.getRetrofit().create(NetworkApi.class);
    }
}
