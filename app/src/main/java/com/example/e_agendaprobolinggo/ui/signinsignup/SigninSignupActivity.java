package com.example.e_agendaprobolinggo.ui.signinsignup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.ui.home.HomeActivity;
import com.example.e_agendaprobolinggo.model.body.Login;
import com.example.e_agendaprobolinggo.model.body.User;
import com.example.e_agendaprobolinggo.ui.signinsignup.customdialogs.SigninDialogFragment;
import com.example.e_agendaprobolinggo.ui.signinsignup.customdialogs.SignupDialogFragment;

public class SigninSignupActivity extends AppCompatActivity
        implements SigninSignupContract.View, SignupDialogFragment.SignupCallback, SigninDialogFragment.SigninCallback {

    SigninDialogFragment signinDialogFragment;
    SignupDialogFragment signupDialogFragment;

    Button btnSignin, btnSignup;
    ProgressBar progressbar;

    SigninSignupContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);

        mPresenter = new SigninSignupPresenter(this);

        //Initializing a bottom sheet
        signinDialogFragment = new SigninDialogFragment();
        signupDialogFragment = new SignupDialogFragment();

        signinDialogFragment.setSigninCallback(this);
        signupDialogFragment.setSignupCallback(this);

        btnSignin = findViewById(R.id.btnSignin);
        btnSignup = findViewById(R.id.btnSignup);
        progressbar = findViewById(R.id.progressBar);

        btnSignin.setOnClickListener(v -> {
            //show it
            signinDialogFragment.show(getSupportFragmentManager(), signinDialogFragment.getTag());
        });

        btnSignup.setOnClickListener(v -> {
            //show it
            signupDialogFragment.show(getSupportFragmentManager(), signupDialogFragment.getTag());
        });

    }

    @Override
    public void onSigninSubmitted(String email, String password) {
        mPresenter.doSignin(new Login(email, password));
        signinDialogFragment.dismiss();
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSignupSubmitted(User user) {
        mPresenter.doSignup(user);
        signupDialogFragment.dismiss();
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifySigninSuccess(String message) {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
            }
        }, 100);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SigninSignupActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }

    @Override
    public void notifySigninFailure(String message) {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
            }
        }, 100);
    }

    @Override
    public void notifySignupSuccess(String message) {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
            }
        }, 100);
    }

    @Override
    public void notifySignupFailure(String message) {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
            }
        }, 100);
    }
}
