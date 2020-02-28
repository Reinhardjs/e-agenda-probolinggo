package com.example.e_agendaprobolinggo.signinsignup;

import com.example.e_agendaprobolinggo.model.body.Login;
import com.example.e_agendaprobolinggo.model.body.User;

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

        void doSignin(Login login, SigninCallback signinCallback);

        void doSignup(User user, SignupCallback signupCallback);

    }

    interface Presenter {

        void doSignin(Login login);

        void doSignup(User user);

    }

}
