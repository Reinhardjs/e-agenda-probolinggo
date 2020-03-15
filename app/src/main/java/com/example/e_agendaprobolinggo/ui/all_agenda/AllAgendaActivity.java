package com.example.e_agendaprobolinggo.ui.all_agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;

import java.util.ArrayList;
import java.util.Objects;

public class AllAgendaActivity extends AppCompatActivity implements AllAgendaContract.View {

    private ArrayList<DataAgenda> agendas = new ArrayList<>();
    private AllAgendaContract.Presenter mPresenter;
    private RecyclerView rvAllAgenda;
    private AllAgendaAdapter allAgendaAdapter;
    private Toolbar toolbarAllAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_agenda);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        mPresenter = new AllAgendaPresenter(this);

        initView();

        mPresenter.getAllAgendaList("all","", "all");
    }

    private void initView() {
        toolbarAllAgenda = findViewById(R.id.toolbar_all_agenda);
        setupToolbar();

//        int[] location = new int[2];
//        toolbarAllAgenda.getLocationOnScreen(location);

        rvAllAgenda = findViewById(R.id.rvAllAgenda);
        rvAllAgenda.setLayoutManager(new LinearLayoutManager(this));
        allAgendaAdapter = new AllAgendaAdapter(agendas, this);
        rvAllAgenda.setAdapter(allAgendaAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbarAllAgenda);
        TextView toolbarTitle = toolbarAllAgenda.findViewById(R.id.toolbar_title_all_agenda);
        toolbarTitle.setText("Semua Agenda");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    @Override
    public void populateAllAgenda(AgendaResponse agendaResponse) {
        new Handler().postDelayed(() -> {
            agendas.addAll(agendaResponse.getData());

            AllAgendaAdapter allAgendaAdapter = (AllAgendaAdapter) Objects.requireNonNull(rvAllAgenda.getAdapter());
            allAgendaAdapter.notifyDataSetChanged();

        }, 1500);
    }

    @Override
    public void showAllAgendaFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
