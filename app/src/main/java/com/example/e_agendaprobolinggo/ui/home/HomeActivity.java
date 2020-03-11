package com.example.e_agendaprobolinggo.ui.home;

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
import com.example.e_agendaprobolinggo.model.response.Agenda;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeContract.Presenter mPresenter;
    private RecyclerView rvCategory, rvAgenda;
    private TextView tvSeeAll;
    private Toolbar toolbar;
    ArrayList<DataAgenda> agendas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter(this);

        initView();
        addListener();

        swipeRefreshLayout.setRefreshing(true);
        mPresenter.requestCategoryList();
        mPresenter.requestAgendaList();
        showShimmer();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("E-Agenda");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        rvCategory = findViewById(R.id.rvCategory);
        rvAgenda = findViewById(R.id.rvAgenda);
        tvSeeAll = findViewById(R.id.tvSeeAll);

        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvAgenda.setLayoutManager(new LinearLayoutManager(this));

        AgendaAdapter adapter = new AgendaAdapter(agendas);
        rvAgenda.setAdapter(adapter);
    }

    private void addListener(){
        swipeRefreshLayout.setOnRefreshListener(() -> {
            agendas.clear();
            mPresenter.requestAgendaList();
            showShimmer();

            AgendaAdapter agendaAdapter = (AgendaAdapter) Objects.requireNonNull(rvAgenda.getAdapter());
            agendaAdapter.notifyDataSetChanged();
        });
    }

    private void showShimmer(){
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
    }

    private void hideShimmer(){
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmerAnimation();
    }

    @Override
    public void populateAgenda(Agenda agenda) {
        new Handler().postDelayed(() -> {
            hideShimmer();
            agendas.addAll(agenda.getData());

            AgendaAdapter agendaAdapter = (AgendaAdapter) Objects.requireNonNull(rvAgenda.getAdapter());
            agendaAdapter.notifyDataSetChanged();

            if (swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override
    public void showAgendaFailure(String message) {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

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
