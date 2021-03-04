package com.example.e_agendaprobolinggo.ui.home;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.request.Search;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;
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

public class HomeInteractor implements HomeContract.Interactor {

    private final NetworkApi networkApi = UtilsApi.getApiService();
    private AgendaResponse agendaResponse = null;
    private AgendaResponse agendaSearchResponse = null;
    private KategoriResponse kategoriResponse = null;

    @Override
    public void requestAgendaList(Agenda agenda, HomeContract.AgendaRequestCallback agendaRequestCallback) {

        networkApi.getAgenda(agenda).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaResponse agendaRes) {
                        agendaResponse = agendaRes;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
//                            if (((HttpException) e).code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                agendaRequestCallback.onAgendaRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
//
                        }

                    }

                    @Override
                    public void onComplete() {
                        if (agendaResponse != null) {
                            if (agendaResponse.isStatus()) {
                                agendaRequestCallback.onAgendaRequestCompleted(agendaResponse);
                            } else {
                                agendaRequestCallback.onAgendaRequestFailure(agendaResponse.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void requestAgendaCategoryList(HomeContract.AgendaCategoryRequestCallback agendaCategoryRequestCallback) {

        networkApi.getCategory().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KategoriResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull KategoriResponse kategoriRes) {
                        kategoriResponse = kategoriRes;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                agendaCategoryRequestCallback.onAgendaCategoryRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (kategoriResponse != null) {
                            if (kategoriResponse.isStatus()) {
                                agendaCategoryRequestCallback.onAgendaCategoryRequestCompleted(kategoriResponse);
                            } else {
                                agendaCategoryRequestCallback.onAgendaCategoryRequestFailure(kategoriResponse.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void requestAgendaSearch(String keyword, HomeContract.SearchRequestCallback searchRequestCallback) {
//        searchRequestCallback.onSearchRequestCompleted(null);
        Search search = new Search(keyword, "all", "all");
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
                        if (e instanceof HttpException){
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
                        if (agendaSearchResponse != null){
                            if (agendaSearchResponse.isStatus()){
                                searchRequestCallback.onSearchRequestCompleted(agendaSearchResponse);
                            }
                            else {
                                searchRequestCallback.onSearchRequestFailure(agendaSearchResponse.getMessage());
                            }
                        }
                    }
                });
    }
}
