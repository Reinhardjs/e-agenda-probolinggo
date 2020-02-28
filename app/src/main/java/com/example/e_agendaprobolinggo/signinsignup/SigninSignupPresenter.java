package com.example.e_agendaprobolinggo.signinsignup;

public class SigninSignupPresenter implements SigninSignupContract.Presenter {

    private SigninSignupContract.View mView;
    private SigninSignupContract.Interactor mInteractor;

    public SigninSignupPresenter(SigninSignupContract.View view){
        mView = view;
        mInteractor = new SigninSignupInteractor();
    }

    @Override
    public void doSignin(String email, String password) {
        mInteractor.doSignin(new SigninSignupContract.SigninCallback() {
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
    public void doSignup(String email, String password) {
        mInteractor.doSignup(new SigninSignupContract.SignupCallback() {
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