package com.example.e_agendaprobolinggo.ui.detail;

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

public class DetailInteractor implements DetailContract.Interactor {

    private NetworkApi networkApi = UtilsApi.getApiService();
    private AgendaResponse detailResponse = null;

    @Override
    public void requestDetailAgenda(String key, DetailContract.DetailAgendaRequestCallback detailAgendaRequestCallback) {

        networkApi.getDetailAgenda(key).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaResponse agendaResponse) {
                        detailResponse = agendaResponse;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
//                            if (((HttpException) e).code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                                ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                                try {
                                    JSONObject jsonObject = new JSONObject(errorResponse.string());
                                    detailAgendaRequestCallback.onDetailAgendaRequestFailure(jsonObject.getString("message"));
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
//                            }
                        }

                    }

                    @Override
                    public void onComplete() {
                        if (detailResponse != null) {
                            if (detailResponse.isStatus()){
                                detailAgendaRequestCallback.onDetailAgendaRequestCompleted(detailResponse);
                            } else {
                                detailAgendaRequestCallback.onDetailAgendaRequestFailure(detailResponse.getMessage());
                            }
                        }
                    }
                });
    }
}
