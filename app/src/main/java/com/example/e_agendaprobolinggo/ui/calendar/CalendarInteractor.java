package com.example.e_agendaprobolinggo.ui.calendar;

import com.example.e_agendaprobolinggo.model.body.AgendaRequest;
import com.example.e_agendaprobolinggo.model.response.AgendaCalendarResponse;
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
    private AgendaCalendarResponse agendaCalendarResponse = null;

    @Override
    public void requestAgendaCalendarList(String agendaId, String limit, String subAgendaId, CalendarContract.AgendaCalendarRequestCallback calendarAgendaRequestCallback) {
        AgendaRequest agendaRequest = new AgendaRequest(agendaId, limit, subAgendaId);
        networkApi.getCalendarAgenda(agendaRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaCalendarResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaCalendarResponse response) {
                        agendaCalendarResponse = response;
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
                        if (agendaCalendarResponse != null) {
                            if (agendaCalendarResponse.isStatus()) {
                                calendarAgendaRequestCallback.onAgendaCalendarRequestCompleted(agendaCalendarResponse);
                            } else {
                                calendarAgendaRequestCallback.onAgendaCalendarRequestFailure(agendaCalendarResponse.getMessage());
                            }
                        }
                    }
                });
    }
}
