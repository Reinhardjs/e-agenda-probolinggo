package com.example.e_agendaprobolinggo.ui.calendar;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.databinding.ActivityCalendarBinding;
import com.example.e_agendaprobolinggo.model.response.AgendaCalendarResponse;

public class CalendarActivity extends AppCompatActivity implements CalendarContract.View {

    private ActivityCalendarBinding binding;
    private CalendarContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();

        mPresenter = new CalendarPresenter(this);
        setupRequest();
    }

    private void setupRequest() {
        showShimmer();
        mPresenter.getAgendaCalendarList("all", "", "all");
    }

    private void setupToolbar() {
        Toolbar toolbarCalendar = binding.toolbarCalendar;
        setSupportActionBar(toolbarCalendar);
        TextView toolbarTitle = toolbarCalendar.findViewById(R.id.toolbar_title_calendar);
        toolbarTitle.setText(getResources().getString(R.string.calendar_title));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void showShimmer() {
        binding.shimmerCalendar.setVisibility(View.VISIBLE);
        binding.shimmerCalendar.startShimmerAnimation();
    }

    private void hideShimmer() {
        binding.shimmerCalendar.setVisibility(View.GONE);
        binding.shimmerCalendar.stopShimmerAnimation();
    }

    @Override
    public void populateAgenda(AgendaCalendarResponse agendaCalendarResponse) {
        new Handler().postDelayed(() -> {
            hideShimmer();
            Bundle bundle = new Bundle();
            bundle.putParcelable(MonthModeFragment.CALENDAR_DATA, agendaCalendarResponse);
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.container_calendar, MonthModeFragment.class, bundle).commit();
        }, 1000);
    }

    @Override
    public void showAgendaFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}