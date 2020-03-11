package com.example.e_agendaprobolinggo.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.Agenda;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View{

    private CategoryContract.Presenter mPresenter;
    private RecyclerView rvAgendaPerCategory;
    public static final String CATEGORY = "category";
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();

        category = getIntent().getStringExtra(CATEGORY);

        mPresenter = new CategoryPresenter(this);
        mPresenter.getCategoryAgendaList(category);
    }

    private void initView(){
        rvAgendaPerCategory = findViewById(R.id.rvAgendaPerCategory);

        rvAgendaPerCategory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void populateCategoryAgenda(Agenda agenda) {
        ArrayList<DataAgenda> agendas = new ArrayList<>();
        agendas.addAll(agenda.getData());

        AgendaPerCategoryAdapter adapter = new AgendaPerCategoryAdapter(agendas);
        rvAgendaPerCategory.setAdapter(adapter);
    }

    @Override
    public void showCategoryAgendaFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
