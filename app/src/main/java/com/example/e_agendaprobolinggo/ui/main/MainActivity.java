package com.example.e_agendaprobolinggo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.response.User;
import com.example.e_agendaprobolinggo.stepper.MyStepperAdapter;
import com.example.e_agendaprobolinggo.ui.home.HomeActivity;
import com.example.e_agendaprobolinggo.ui.signinsignup.SigninSignupActivity;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class MainActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    @Override
    protected void onStart() {
        super.onStart();

        User user = SharedPreferenceUtils.getUser(getApplicationContext());
        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StepperLayout mStepperLayout = findViewById(R.id.stepperLayout);
        mStepperLayout.setBackButtonColor(getResources().getColor(R.color.ms_black));
        mStepperLayout.setCompleteButtonColor(getResources().getColor(R.color.ms_white));
        mStepperLayout.setNextButtonColor(getResources().getColor(R.color.ms_white));
        mStepperLayout.setAdapter(new MyStepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {
        Intent intent = new Intent(MainActivity.this, SigninSignupActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(VerificationError verificationError) {
    }

    @Override
    public void onStepSelected(int newStepPosition) {
    }

    @Override
    public void onReturn() {
        finish();
    }
}
