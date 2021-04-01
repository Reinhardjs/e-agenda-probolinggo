package com.example.e_agendaprobolinggo.ui.signinsignup;

import android.os.Handler;

import com.example.e_agendaprobolinggo.model.request.Login;
import com.example.e_agendaprobolinggo.model.request.Register;
import com.example.e_agendaprobolinggo.model.response.LoginResponse;
import com.example.e_agendaprobolinggo.model.response.RegisterResponse;
import com.example.e_agendaprobolinggo.network.NetworkApi;
import com.example.e_agendaprobolinggo.network.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class SigninSignupInteractor implements SigninSignupContract.Interactor {

    private final NetworkApi networkApi = UtilsApi.getApiService();
    private LoginResponse loginRes = null;
    private RegisterResponse registerRes = null;
    private ResponseBody errorResponse = null;

    @Override
    public void doSignin(Login login, final SigninSignupContract.SigninCallback signinCallback) {
        new Handler().postDelayed(() -> networkApi.loginUser(login).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LoginResponse loginResponse) {
                        loginRes = loginResponse;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            errorResponse = Objects.requireNonNull(((HttpException) e).response()).errorBody();
                            try {
                                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(errorResponse).string());
                                signinCallback.onSigninFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (loginRes != null) {
                            if (loginRes.isStatus()) {
                                if (loginRes.getUser().getStatus().equalsIgnoreCase("ENABLE")) {
                                    signinCallback.onSigninSuccess(loginRes);
                                } else {
                                    signinCallback.onSigninFailure(loginRes.getMessage());
                                }

                            } else {
                                signinCallback.onSigninFailure(loginRes.getMessage());
                            }
                        }
                    }
                }), 2000);
    }

    @Override
    public void doSignup(Register register, final SigninSignupContract.SignupCallback signupCallback) {
        new Handler().postDelayed(() -> networkApi.registerUser(register).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread()).subscribe(new Observer<RegisterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegisterResponse registerResponse) {
                        registerRes = registerResponse;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            errorResponse = Objects.requireNonNull(((HttpException) e).response()).errorBody();
                            try {
                                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(errorResponse).string());
                                signupCallback.onSignupFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (registerRes != null) {
                            if (registerRes.isStatus()) {
                                signupCallback.onSignupSuccess(registerRes.getMessage());
                            } else {
                                signupCallback.onSignupFailure(registerRes.getMessage());
                            }
                        }
                    }
                }), 2000);
    }

}
