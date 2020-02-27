package com.example.e_agendaprobolinggo.network;

import com.example.e_agendaprobolinggo.model.body.User;
import com.example.e_agendaprobolinggo.model.response.Agenda;
import com.example.e_agendaprobolinggo.model.response.DataItem;
import com.example.e_agendaprobolinggo.model.response.UserResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
//import rx.Observable;

public interface NetworkApi {

    @POST("daftar")
    Observable<UserResponse> registerUser(
            @Body User user
            );

    @FormUrlEncoded
    @POST("masuk")
    Observable<UserResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("data")
    Observable<Agenda> getAgenda();

//    @GET("data/{id}")
//    Observable<DataItem> getDetailAgenda(
//
//    );
}
