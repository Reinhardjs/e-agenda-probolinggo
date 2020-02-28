package com.example.e_agendaprobolinggo.signinsignup;

import com.example.e_agendaprobolinggo.model.body.Login;
import com.example.e_agendaprobolinggo.model.body.User;

public class SigninSignupPresenter implements SigninSignupContract.Presenter {

    private SigninSignupContract.View mView;
    private SigninSignupContract.Interactor mInteractor;

    public SigninSignupPresenter(SigninSignupContract.View view){
        mView = view;
        mInteractor = new SigninSignupInteractor();
    }

    @Override
    public void doSignin(Login login) {
        mInteractor.doSignin(login, new SigninSignupContract.SigninCallback() {
            @Override
            public void onSigninSuccess(String message) {
                mView.notifySigninSuccess(message);
            }

            @Override
            public void onSignupFailure(String message) {
                mView.notifySigninFailure(message);
            }
        });
    }

    @Override
    public void doSignup(User user) {
        mInteractor.doSignup(user, new SigninSignupContract.SignupCallback() {
            @Override
            public void onSignupSuccess(String message) {
                mView.notifySignupSuccess(message);
            }

            @Override
            public void onSignupFailure(String message) {
                mView.notifySignupFailure(message);
            }
        });
    }

}
