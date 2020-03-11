package com.example.e_agendaprobolinggo.ui.category;

import com.example.e_agendaprobolinggo.model.response.Agenda;
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

public class CategoryInteractor implements CategoryContract.Interactor {

    private NetworkApi networkApi = UtilsApi.getApiService();
    private Agenda agendaPerCategory;

    @Override
    public void requestAgendaList(String category, CategoryContract.CategoryAgendaRequestCallback categoryAgendaRequestCallback) {
        networkApi.getAgendaPerCategory(category, "all").subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Agenda>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Agenda agenda) {
                        agendaPerCategory = agenda;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
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

                    @Override
                    public void onComplete() {
                        if (agendaPerCategory != null){
                            if (agendaPerCategory.isStatus()){
                                categoryAgendaRequestCallback.onCategoryAgendaRequestCompleted(agendaPerCategory);
                            }
                            else {
                                categoryAgendaRequestCallback.onCategoryAgendaRequestFailure(agendaPerCategory.getMessage());
                            }
                        }
                    }
                });
    }
}
