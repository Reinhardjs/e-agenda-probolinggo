package com.example.e_agendaprobolinggo.home;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.Agenda;
import com.example.e_agendaprobolinggo.model.response.DataItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private HomeContract.Presenter mPresenter;
    private RecyclerView rvCategory, rvAgenda;
    private TextView tvSeeAll;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

        mPresenter = new HomePresenter(this);
        mPresenter.getAgendaList();
        mPresenter.getCategoryList();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("E-Agenda");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvCategory = findViewById(R.id.rvCategory);
        rvAgenda = findViewById(R.id.rvAgenda);
        tvSeeAll = findViewById(R.id.tvSeeAll);

        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvAgenda.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void populateAgenda(Agenda agenda) {
        ArrayList<DataItem> agendas = new ArrayList<>();
        agendas.addAll(agenda.getData());

        AgendaAdapter adapter = new AgendaAdapter(agendas);
        rvAgenda.setAdapter(adapter);
    }

    @Override
    public void showAgendaFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateCategory(ArrayList<String> categories) {
        rvCategory.setAdapter(new CategoryAdapter(categories));
    }

    @Override
    public void showCategoryFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
