package com.example.e_agendaprobolinggo.signinsignup;

import android.os.Handler;

public class SigninSignupInteractor implements SigninSignupContract.Interactor {

    @Override
    public void doSignin(final SigninSignupContract.SigninCallback signinCallback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (true){
                    signinCallback.onSigninSuccess("Berhasil masuk");
                } else {
                    signinCallback.onSignupFailure("Gagal masuk");
                }
            }
        }, 2000);
    }

    @Override
    public void doSignup(final SigninSignupContract.SignupCallback signupCallback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (true){
                    signupCallback.onSignupSuccess("Berhasil mendaftar");
                } else {
                    signupCallback.onSignupFailure("Gagal mendaftar");
                }
            }
        }, 2000);
    }

}
