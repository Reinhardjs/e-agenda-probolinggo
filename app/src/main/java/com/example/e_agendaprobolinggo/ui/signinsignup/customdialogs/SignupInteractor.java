package com.example.e_agendaprobolinggo.ui.signinsignup.customdialogs;

import com.example.e_agendaprobolinggo.model.response.RoleResponse;
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

public class SignupInteractor implements SignupContract.Interactor {

    private final NetworkApi networkApi = UtilsApi.getApiService();
    private RoleResponse roleResponse = null;

    @Override
    public void requestRoleUser(SignupContract.RoleUserCallback roleUserCallback) {
        networkApi.getRoleUser().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RoleResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RoleResponse response) {
                        roleResponse = response;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException){
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                roleUserCallback.onRoleRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (roleResponse != null) {
                            if (roleResponse.isStatus()) {
                                roleUserCallback.onRoleRequestCompleted(roleResponse);
                            } else {
                                roleUserCallback.onRoleRequestFailure(roleResponse.getMessage());
                            }
                        }
                    }
                });
    }
}
