package com.example.e_agendaprobolinggo.signinsignup;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.e_agendaprobolinggo.model.body.Login;
import com.example.e_agendaprobolinggo.model.body.User;
import com.example.e_agendaprobolinggo.model.response.UserResponse;
import com.example.e_agendaprobolinggo.network.NetworkApi;
import com.example.e_agendaprobolinggo.network.NetworkClient;
import com.example.e_agendaprobolinggo.network.UtilsApi;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninSignupInteractor implements SigninSignupContract.Interactor {

    private NetworkApi networkApi = UtilsApi.getApiService();
    private UserResponse response;

    @Override
    public void doSignin(Login login, final SigninSignupContract.SigninCallback signinCallback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Call<UserResponse> loginCall = networkApi.loginUser(login);
                loginCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                signinCallback.onSigninSuccess(response.body().getMessage());
                            } else {
                                signinCallback.onSignupFailure(response.body().getMessage());
                            }
                        }
                        else {
//                            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                            try {
                                Log.d("tes login: ", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("coba2: ", t.getMessage());
                    }
                });
//                networkApi.loginUser(login).subscribeOn(Schedulers.io()).subscribe(new Observer<UserResponse>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull UserResponse userResponse) {
//                        Log.d("tess: ", userResponse.getMessage());
//                        if (userResponse.isStatus()){
//                            signinCallback.onSigninSuccess(userResponse.getMessage());
//                        } else {
//                            signinCallback.onSignupFailure(userResponse.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//                if (false){
//                    signinCallback.onSigninSuccess("Berhasil masuk");
//                } else {
//                    signinCallback.onSignupFailure("Gagal masuk");
//                }
            }
        }, 2000);
    }

    @Override
    public void doSignup(User user, final SigninSignupContract.SignupCallback signupCallback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Call<UserResponse> registerCall = networkApi.registerUser(user);

                registerCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                signupCallback.onSignupSuccess(response.body().getMessage());
                            } else {
                                signupCallback.onSignupFailure(response.body().getMessage());
                            }
                        }
                        else {
//                            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                            try {
                                Log.d("tes regis", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("tes regis2 ", t.getMessage());
                    }
                });

//                networkApi.registerUser(user).subscribeOn(Schedulers.io()).subscribe(new Observer<UserResponse>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull UserResponse userResponse) {
//                        response = userResponse;
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        if (response.isStatus()){
//                            signupCallback.onSignupSuccess(response.getMessage());
//                        } else {
//                            signupCallback.onSignupFailure(response.getMessage());
//                        }
//                    }
//                });

//                if (true) {
//                    signupCallback.onSignupSuccess("Berhasil mendaftar");
//                } else {
//                    signupCallback.onSignupFailure("Gagal mendaftar");
//                }
            }
        }, 2000);
    }

}
