package com.example.e_agendaprobolinggo.ui.calendar;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.databinding.ActivityCalendarBinding;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.User;
import com.google.android.material.appbar.MaterialToolbar;

public class CalendarActivity extends AppCompatActivity implements CalendarContract.View {

    public static final String CALENDAR_DATA = "calendar_data";

    private ActivityCalendarBinding binding;
    private CalendarContract.Presenter mPresenter;
    private AgendaResponse agendaResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();

        User user = SharedPreferenceUtils.getUser(this);
        String idUser = user.getId();

        Agenda agenda = new Agenda("all", "", idUser, "all");

        mPresenter = new CalendarPresenter(this);
        setupRequest(agenda);
    }

    private void setupRequest(Agenda agenda) {
        showShimmer();
        mPresenter.getAgendaCalendarList(agenda);
    }

    private void setupToolbar() {
        MaterialToolbar toolbarCalendar = binding.toolbarCalendar;
        setSupportActionBar(toolbarCalendar);
        TextView toolbarTitle = toolbarCalendar.findViewById(R.id.toolbar_title_calendar);
        toolbarTitle.setText(getResources().getString(R.string.calendar_title));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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
    public void populateAgenda(AgendaResponse response) {
        agendaResponse = response;
        new Handler().postDelayed(() -> {
            hideShimmer();
            Bundle bundle = new Bundle();
            bundle.putParcelable(CALENDAR_DATA, agendaResponse);
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.container_calendar, MonthModeFragment.class, bundle).commit();
        }, 1000);
    }

    @Override
    public void showAgendaFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.month_mode_item) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(CALENDAR_DATA, agendaResponse);
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.container_calendar, MonthModeFragment.class, bundle).commit();
            return true;
        } else if (id == R.id.date_mode_item) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(CALENDAR_DATA, agendaResponse);
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.container_calendar, DateModeFragment.class, bundle).commit();
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}