package com.example.e_agendaprobolinggo.ui.category;

import com.example.e_agendaprobolinggo.model.body.AgendaRequest;
import com.example.e_agendaprobolinggo.model.body.SearchRequest;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.network.NetworkApi;
import com.example.e_agendaprobolinggo.network.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class CategoryInteractor implements CategoryContract.Interactor {

    private NetworkApi networkApi = UtilsApi.getApiService();
    private AgendaResponse agendaResponsePerCategory;
    private AgendaResponse agendaResponseSearch;

    @Override
    public void requestAgendaList(String agendaId, String subAgendaId, CategoryContract.CategoryAgendaRequestCallback categoryAgendaRequestCallback) {
        AgendaRequest agendaRequest = new AgendaRequest(agendaId, "", subAgendaId);
        networkApi.getAgenda(agendaRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaResponse agendaResponse) {
                        agendaResponsePerCategory = agendaResponse;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                categoryAgendaRequestCallback.onCategoryAgendaRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onComplete() {
                        if (agendaResponsePerCategory != null) {
                            if (agendaResponsePerCategory.isStatus()) {

                                categoryAgendaRequestCallback.onCategoryAgendaRequestCompleted(agendaResponsePerCategory);
                            } else {
                                categoryAgendaRequestCallback.onCategoryAgendaRequestFailure(agendaResponsePerCategory.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void requestAgendaPerCategorySearch(String keyword, String agendaId, String subAgendaId, CategoryContract.AgendaPerCategorySearchRequestCallBack agendaPerCategorySearchRequestCallBack) {
        SearchRequest searchRequest = new SearchRequest(keyword, agendaId, subAgendaId);

        networkApi.getAgendaSearch(searchRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaResponse agendaResponse) {
                        agendaResponseSearch = agendaResponse;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                agendaPerCategorySearchRequestCallBack.onAgendaPerCategorySearchRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (agendaResponseSearch != null) {
                            if (agendaResponseSearch.isStatus()) {
                                agendaPerCategorySearchRequestCallBack.onAgendaPerCategorySearchRequestCompleted(agendaResponseSearch);
                            } else {
                                agendaPerCategorySearchRequestCallBack.onAgendaPerCategorySearchRequestFailure(agendaResponseSearch.getMessage());
                            }
                        }
                    }
                });
    }

}
