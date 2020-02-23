package com.example.e_agendaprobolinggo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_agendaprobolinggo.stepper.MyStepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class MainActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        View showModalBottomSheet = findViewById(R.id.as_modal);
//        showModalBottomSheet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Initializing a bottom sheet
//                BottomSheetDialogFragment bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
//
//                //show it
//                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
//            }
//        });

//        //Initializing a bottom sheet
//        BottomSheetDialogFragment bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
//
//        //show it
//        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
//
        mStepperLayout = findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new MyStepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {
//        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
//        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
//        Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }
}
