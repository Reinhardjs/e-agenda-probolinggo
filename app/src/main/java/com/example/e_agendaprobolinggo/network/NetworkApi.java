package com.example.e_agendaprobolinggo.network;

import com.example.e_agendaprobolinggo.model.body.Login;
import com.example.e_agendaprobolinggo.model.body.User;
import com.example.e_agendaprobolinggo.model.response.Agenda;
import com.example.e_agendaprobolinggo.model.response.LoginResponse;
import com.example.e_agendaprobolinggo.model.response.RegisterResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetworkApi {

    @Headers("x-sm-key: 35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("daftar")
    Observable<RegisterResponse> registerUser(
            @Body User user
            );

    @Headers("x-sm-key: 35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("masuk")
    Observable<LoginResponse> loginUser(
            @Body Login login
            );

    @Headers("x-sm-key:35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("data")
    Observable<Agenda> getAgenda();

//    @GET("data/{id}")
//    Observable<DataItem> getDetailAgenda(
//
//    );
}
