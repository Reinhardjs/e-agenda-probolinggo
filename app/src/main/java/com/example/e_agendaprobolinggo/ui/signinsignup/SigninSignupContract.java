package com.example.e_agendaprobolinggo.ui.signinsignup;

import com.example.e_agendaprobolinggo.model.request.Login;
import com.example.e_agendaprobolinggo.model.request.Register;

public interface SigninSignupContract {

    interface SigninCallback {

        void onSigninSuccess(String message);

        void onSigninFailure(String message);

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

        void doSignup(Register register, SignupCallback signupCallback);

    }

    interface Presenter {

        void doSignin(Login login);

        void doSignup(Register register);

    }

}
