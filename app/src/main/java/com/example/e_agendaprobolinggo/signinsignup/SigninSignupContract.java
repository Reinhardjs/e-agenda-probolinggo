package com.example.e_agendaprobolinggo.signinsignup;

public interface SigninSignupContract {

    interface SigninCallback {

        void onSigninSuccess(String message);

        void onSignupFailure(String message);

    }

    interface SignupCallback {

        void onSignupSuccess(String message);

        void onSignupFailure(String message);

    }

    interface View {

        void notifySigninSuccess(String message);

        void notifySigninFailure(String message);

        void notifySignupSuccess(String message);

        void notifySignupFailure(String message);

    }

    interface Interactor {

        void doSignin(SigninCallback signinCallback);

        void doSignup(SignupCallback signupCallback);

    }

    interface Presenter {

        void doSignin(String email, String password);

        void doSignup(String email, String password);

    }

}
