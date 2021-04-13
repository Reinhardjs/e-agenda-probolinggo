package com.karyadev.e_agendaprobolinggo.network;

import com.karyadev.e_agendaprobolinggo.model.request.Agenda;
import com.karyadev.e_agendaprobolinggo.model.request.DeleteComment;
import com.karyadev.e_agendaprobolinggo.model.request.DetailAgenda;
import com.karyadev.e_agendaprobolinggo.model.request.Login;
import com.karyadev.e_agendaprobolinggo.model.request.NewComment;
import com.karyadev.e_agendaprobolinggo.model.request.Register;
import com.karyadev.e_agendaprobolinggo.model.request.Search;
import com.karyadev.e_agendaprobolinggo.model.response.AddCommentResponse;
import com.karyadev.e_agendaprobolinggo.model.response.AgendaResponse;
import com.karyadev.e_agendaprobolinggo.model.response.DeleteCommentResponse;
import com.karyadev.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.karyadev.e_agendaprobolinggo.model.response.KategoriResponse;
import com.karyadev.e_agendaprobolinggo.model.response.LoginResponse;
import com.karyadev.e_agendaprobolinggo.model.response.RegisterResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkApi {

    @POST("agenda_v2/daftar")
    Observable<RegisterResponse> registerUser(
            @Body Register register
    );

    @POST("agenda_v2/masuk")
    Observable<LoginResponse> loginUser(
            @Body Login login
    );

    @POST("agenda/data")
    Observable<AgendaResponse> getAgenda(
            @Body Agenda agenda
    );

    @POST("agenda_v2/data")
    Observable<AgendaResponse> getAgendaCalendar(
            @Body Agenda agenda
    );

    @POST("agenda_v2/detail")
    Observable<DetailAgendaResponse> getDetailAgenda(
            @Body DetailAgenda detailAgenda
    );

    @POST("agenda/pencarian")
    Observable<AgendaResponse> getAgendaSearch(
            @Body Search search
    );

    @POST("agenda_v2/kategori")
    Observable<KategoriResponse> getCategory();

    @POST("agenda_v2/tambah_komentar")
    Observable<AddCommentResponse> addComment(
            @Body NewComment newComment
    );

    @POST("agenda_v2/hapus_komentar")
    Observable<DeleteCommentResponse> deleteComment(
            @Body DeleteComment deleteComment
    );
}
