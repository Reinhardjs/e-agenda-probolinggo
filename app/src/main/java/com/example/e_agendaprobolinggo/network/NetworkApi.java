package com.example.e_agendaprobolinggo.network;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.request.Login;
import com.example.e_agendaprobolinggo.model.request.Register;
import com.example.e_agendaprobolinggo.model.request.Search;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;
import com.example.e_agendaprobolinggo.model.response.LoginResponse;
import com.example.e_agendaprobolinggo.model.response.RegisterResponse;
import com.example.e_agendaprobolinggo.model.response.RoleResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetworkApi {

    @Headers("x-sm-key: 35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("tingkatan")
    Observable<RoleResponse> getRoleUser();

    @Headers("x-sm-key: 35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("daftar")
    Observable<RegisterResponse> registerUser(
            @Body Register register
    );

    @Headers("x-sm-key: 35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("masuk")
    Observable<LoginResponse> loginUser(
            @Body Login login
    );

    @Headers("x-sm-key:35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("data")
    Observable<AgendaResponse> getAgenda(
            @Body Agenda agenda
    );

    @Headers("x-sm-key:35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("detail")
    Observable<DetailAgendaResponse> getDetailAgenda(
            @Body DetailAgenda detailAgenda
    );

    @Headers("x-sm-key:35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("pencarian")
    Observable<AgendaResponse> getAgendaSearch(
            @Body Search search
    );

    @Headers("x-sm-key:35d3d08c3d7b7f445ceb8e726a87b26c")
    @POST("kategori")
    Observable<KategoriResponse> getCategory();

}
