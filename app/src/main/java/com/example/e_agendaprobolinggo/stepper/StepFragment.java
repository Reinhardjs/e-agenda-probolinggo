package com.example.e_agendaprobolinggo.stepper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.e_agendaprobolinggo.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class StepFragment extends Fragment implements Step {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step, container, false);
        String title = getArguments().getString("title");
        String description = getArguments().getString("description");
        int imageRes = getArguments().getInt("imageRes");

        //initialize your UI
        TextView titleTv = v.findViewById(R.id.title);
        titleTv.setText(title);

        TextView descriptionTv = v.findViewById(R.id.description);
        descriptionTv.setText(description);

        ImageView imageView = v.findViewById(R.id.imageView);
        // imageView.setImageResource(imageRes);

        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

}