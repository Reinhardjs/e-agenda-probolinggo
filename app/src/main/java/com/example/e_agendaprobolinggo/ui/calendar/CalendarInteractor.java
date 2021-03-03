package com.example.e_agendaprobolinggo.ui.calendar;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.network.NetworkApi;
import com.example.e_agendaprobolinggo.network.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class CalendarInteractor implements CalendarContract.Interactor {

    private NetworkApi networkApi = UtilsApi.getApiService();
    private AgendaResponse agendaResponse = null;

    @Override
    public void requestAgendaCalendarList(String agendaId, String limit, String subAgendaId, CalendarContract.AgendaCalendarRequestCallback calendarAgendaRequestCallback) {
        Agenda agenda = new Agenda(agendaId, limit, subAgendaId);
        networkApi.getAgenda(agenda).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaResponse response) {
                        agendaResponse = response;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                calendarAgendaRequestCallback.onAgendaCalendarRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (agendaResponse != null) {
                            if (agendaResponse.isStatus()) {
                                calendarAgendaRequestCallback.onAgendaCalendarRequestCompleted(agendaResponse);
                            } else {
                                calendarAgendaRequestCallback.onAgendaCalendarRequestFailure(agendaResponse.getMessage());
                            }
                        }
                    }
                });
    }
}
