package com.example.e_agendaprobolinggo.ui.all_agenda;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.request.Search;
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

public class AllAgendaInteractor implements AllAgendaContract.Interactor {

    private final NetworkApi networkApi = UtilsApi.getApiService();
    private AgendaResponse allAgendaResponse = null;
    private AgendaResponse agendaSearchResponse = null;

    @Override
    public void requestAllAgendaList(Agenda agenda, AllAgendaContract.AllAgendaRequestCallback allAgendaRequestCallback) {
        networkApi.getAgenda(agenda).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaResponse agendaResponse) {
                        allAgendaResponse = agendaResponse;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                allAgendaRequestCallback.onAllAgendaRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (allAgendaResponse != null) {
                            if (allAgendaResponse.isStatus()) {
                                allAgendaRequestCallback.onAllAgendaRequestCompleted(allAgendaResponse);
                            } else {
                                allAgendaRequestCallback.onAllAgendaRequestFailure(allAgendaResponse.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void requestAgendaSearch(Search search, AllAgendaContract.SearchRequestCallback searchRequestCallback) {
        networkApi.getAgendaSearch(search).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaResponse agendaResponse) {
                        agendaSearchResponse = agendaResponse;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                searchRequestCallback.onSearchRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (agendaSearchResponse != null) {
                            if (agendaSearchResponse.isStatus()) {
                                searchRequestCallback.onSearchRequestCompleted(agendaSearchResponse);
                            } else {
                                searchRequestCallback.onSearchRequestFailure(agendaSearchResponse.getMessage());
                            }
                        }
                    }
                });
    }
}
