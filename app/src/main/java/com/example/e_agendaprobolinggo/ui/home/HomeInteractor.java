package com.example.e_agendaprobolinggo.ui.home;

import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LifecycleRegistryOwner;

import com.example.e_agendaprobolinggo.model.body.AgendaRequest;
import com.example.e_agendaprobolinggo.model.body.SearchRequest;
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

    private NetworkApi networkApi = UtilsApi.getApiService();
    private AgendaResponse agendaResponse = null;
    private AgendaResponse agendaSearchResponse = null;
    private KategoriResponse kategoriResponse = null;

    @Override
    public void requestAgendaList(HomeContract.AgendaRequestCallback agendaRequestCallback) {

        AgendaRequest agendaRequest = new AgendaRequest("all", "5", "all");
        networkApi.getAgenda(agendaRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
    public void requestAgendaTypeList(HomeContract.AgendaTypeRequestCallback agendaTypeRequestCallback) {

        networkApi.getKategory().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                                agendaTypeRequestCallback.onAgendaTypeRequestFailure(jsonObject.getString("message"));
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
                                agendaTypeRequestCallback.onAgendaTypeRequestCompleted(kategoriResponse);
                            } else {
                                agendaTypeRequestCallback.onAgendaTypeRequestFailure(kategoriResponse.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void requestAgendaSearch(String keyword, HomeContract.SearchRequestCallback searchRequestCallback) {
//        searchRequestCallback.onSearchRequestCompleted(null);
        SearchRequest searchRequest = new SearchRequest(keyword, "all", "all");
        networkApi.getAgendaSearch(searchRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
