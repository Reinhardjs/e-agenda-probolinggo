package com.example.e_agendaprobolinggo.stepper;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.e_agendaprobolinggo.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.ArrayList;

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<Integer> imageRes = new ArrayList<>();

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);

        titles.add("Apa itu E-Agenda");
        titles.add("Mengapa E-Agenda");
        titles.add("Yuk mulai");

        descriptions.add("E-Agenda merupakan media yang \n" +
                "disediakan pemerintah Kabupaten \n" +
                "Probolinggo untuk kemudahan \n" +
                "masyarakat memperoleh informasi \n" +
                "jadwal kegiatan pemerintah Daerah");

        descriptions.add("E-Agenda lahir dari semangat \n" +
                "transparasi publik terhadap kegiatan \n" +
                "Pemerintah Daerah Probolinggo");

        descriptions.add("");


        imageRes.add(R.drawable.slide1);
        imageRes.add(R.drawable.slide2);
        imageRes.add(R.drawable.slide3);
    }

    @Override
    public Step createStep(int position) {
        final StepFragment step = new StepFragment();
        Bundle b = new Bundle();
        b.putInt("step_position", position);
        b.putString("title", titles.get(position));
        b.putString("description", descriptions.get(position));
        b.putInt("imageRes", imageRes.get(position));
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        if (titles.size()-1 == position) {
            return new StepViewModel.Builder(context)
                    // .setBackButtonStartDrawableResId(R.drawable.ic_navigate_back_black_24dp)
                    .setBackButtonLabel("KEMBALI")
                    .setEndButtonLabel("MULAI")
                    // .setTitle("Title") //can be a CharSequence instead
                    .create();
        } else {
            return new StepViewModel.Builder(context)
                    .setEndButtonLabel("")
                    .setBackButtonLabel("KEMBALI")
                    // .setBackButtonStartDrawableResId(R.drawable.ic_navigate_back_black_24dp)
                    .setNextButtonEndDrawableResId(R.drawable.ic_navigate_next_black_24dp)
                    // .setTitle("Title") //can be a CharSequence instead
                    .create();
        }
    }
}