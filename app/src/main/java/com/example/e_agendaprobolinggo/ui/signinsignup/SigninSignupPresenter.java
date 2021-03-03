package com.example.e_agendaprobolinggo.ui.signinsignup;

import com.example.e_agendaprobolinggo.model.request.Login;
import com.example.e_agendaprobolinggo.model.request.Register;

public class SigninSignupPresenter implements SigninSignupContract.Presenter {

    private final SigninSignupContract.View mView;
    private final SigninSignupContract.Interactor mInteractor;

    public SigninSignupPresenter(SigninSignupContract.View view) {
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
            public void onSigninFailure(String message) {
                mView.notifySigninFailure(message);
            }
        });
    }

    @Override
    public void doSignup(Register register) {
        mInteractor.doSignup(register, new SigninSignupContract.SignupCallback() {
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
