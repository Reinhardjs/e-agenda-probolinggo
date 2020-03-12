package com.example.e_agendaprobolinggo.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {

    private CategoryContract.Presenter mPresenter;
    private RecyclerView rvAgendaPerCategory;
    public static final String AGENDA_ID = "agenda_id";
    public static final String SUB_AGENDA_ID = "sub_agenda_id";
    private String agendaId, subAgendaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();

        agendaId = getIntent().getStringExtra(AGENDA_ID);
        subAgendaId = getIntent().getStringExtra(SUB_AGENDA_ID);

        mPresenter = new CategoryPresenter(this);
        mPresenter.getCategoryAgendaList(agendaId, subAgendaId);
    }

    private void initView() {
        rvAgendaPerCategory = findViewById(R.id.rvAgendaPerCategory);

        rvAgendaPerCategory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void populateCategoryAgenda(AgendaResponse agendaResponse) {

        new Handler().postDelayed(() -> {
            ArrayList<DataAgenda> agendas = new ArrayList<>();

            agendas.addAll(agendaResponse.getData());

            AgendaPerCategoryAdapter adapter = new AgendaPerCategoryAdapter(agendas);
            rvAgendaPerCategory.setAdapter(adapter);

        }, 1500);


    }

    @Override
    public void showCategoryAgendaFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
