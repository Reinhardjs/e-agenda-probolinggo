package com.example.e_agendaprobolinggo.ui.category;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {

    public static final String AGENDA_ID = "agenda_id";
    public static final String SUB_AGENDA_ID = "sub_agenda_id";
    public static final String SUB_AGENDA_NAME = "sub_agenda_name";

    private ArrayList<DataAgenda> agendas = new ArrayList<>();
    private RecyclerView rvAgendaPerCategory;
    private AgendaPerCategoryAdapter agendaPerCategoryAdapter;

    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;

    private String agendaId, subAgendaId, subAgendaName;
    private CategoryContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        agendaId = getIntent().getStringExtra(AGENDA_ID);
        subAgendaId = getIntent().getStringExtra(SUB_AGENDA_ID);
        subAgendaName = getIntent().getStringExtra(SUB_AGENDA_NAME);

        initView();
        addListener();

        mPresenter = new CategoryPresenter(this);
        mPresenter.getCategoryAgendaList(agendaId, subAgendaId);

        swipeRefreshLayout.setRefreshing(true);
        showShimmer();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();

        rvAgendaPerCategory = findViewById(R.id.rvAgendaPerCategory);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutCategory);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_category);

        rvAgendaPerCategory.setLayoutManager(new LinearLayoutManager(this));

        agendaPerCategoryAdapter = new AgendaPerCategoryAdapter(agendas);
        rvAgendaPerCategory.setAdapter(agendaPerCategoryAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Agenda " + subAgendaName);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void addListener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            agendas.clear();
            mPresenter.getCategoryAgendaList(agendaId, subAgendaId);
            showShimmer();

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
