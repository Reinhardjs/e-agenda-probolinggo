package com.example.e_agendaprobolinggo.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {

    private ArrayList<DataAgenda> agendas = new ArrayList<>();
    private CategoryContract.Presenter mPresenter;
    private RecyclerView rvAgendaPerCategory;
    public static final String AGENDA_ID = "agenda_id";
    public static final String SUB_AGENDA_ID = "sub_agenda_id";
    private String agendaId, subAgendaId;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AgendaPerCategoryAdapter agendaPerCategoryAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mPresenter = new CategoryPresenter(this);

        initView();
        addListener();

        agendaId = getIntent().getStringExtra(AGENDA_ID);
        subAgendaId = getIntent().getStringExtra(SUB_AGENDA_ID);

        mPresenter.getCategoryAgendaList(agendaId, subAgendaId);

        swipeRefreshLayout.setRefreshing(true);

        showShimmer();
    }

    private void initView() {
        rvAgendaPerCategory = findViewById(R.id.rvAgendaPerCategory);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutCategory);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_category);

        rvAgendaPerCategory.setLayoutManager(new LinearLayoutManager(this));

        agendaPerCategoryAdapter = new AgendaPerCategoryAdapter(agendas);
        rvAgendaPerCategory.setAdapter(agendaPerCategoryAdapter);

    }

    private void addListener(){
        swipeRefreshLayout.setOnRefreshListener(() -> {
            agendas.clear();
            mPresenter.getCategoryAgendaList(agendaId, subAgendaId);
            showShimmer();

            // AgendaAdapter agendaAdapter = (AgendaAdapter) Objects.requireNonNull(rvAgenda.getAdapter());
            agendaPerCategoryAdapter.notifyDataSetChanged();
        });
    }

    private void showShimmer() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
    }

    private void hideShimmer() {
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmerAnimation();
    }

    @Override
    public void populateCategoryAgenda(AgendaResponse agendaResponse) {

        new Handler().postDelayed(() -> {
            hideShimmer();
            agendas.addAll(agendaResponse.getData());

            AgendaPerCategoryAdapter agendaPerCategoryAdapter = (AgendaPerCategoryAdapter) Objects.requireNonNull(rvAgendaPerCategory.getAdapter());
            agendaPerCategoryAdapter.notifyDataSetChanged();

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }

//            agendaPerCategoryAdapter = new AgendaPerCategoryAdapter(agendas);
//            rvAgendaPerCategory.setAdapter(agendaPerCategoryAdapter);

        }, 1500);


    }

    @Override
    public void showCategoryAgendaFailure(String message) {
        hideShimmer();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
