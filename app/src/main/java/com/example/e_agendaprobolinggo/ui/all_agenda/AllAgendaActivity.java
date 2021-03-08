package com.example.e_agendaprobolinggo.ui.all_agenda;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.request.Search;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.model.response.User;
import com.example.e_agendaprobolinggo.ui.home.AgendaAdapter;
import com.example.e_agendaprobolinggo.ui.home.customsearchutils.AnchorSheetBehavior;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Objects;

public class AllAgendaActivity extends AppCompatActivity implements AllAgendaContract.View {

    ArrayList<DataAgenda> agendas = new ArrayList<>();
    ArrayList<DataAgenda> agendaSearches = new ArrayList<>();

    private RecyclerView rvAllAgenda, rvAgendaSearch;
    private AllAgendaAdapter allAgendaAdapter;
    private AgendaAdapter agendaSearchAdapter;

    private MaterialToolbar toolbarAllAgenda;

    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MaterialSearchView materialSearchView;
    private AnchorSheetBehavior<View> anchorBehavior;

    private ProgressBar searchProgressBar;

    private AllAgendaContract.Presenter mPresenter;

    private Agenda agenda;

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
            anchorBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.default_menu_item, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_agenda);

        // Ini untuk mengatasi masalah result anchorsheet yang ketutup toolbar pas keyboard muncul
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        initView();
        setupAllRecyclerViews();
        setupAnchorSheetBehavior();
        setupListenerOrCallback();

        User user = SharedPreferenceUtils.getUser(this);
        String idUser = user.getId();

        agenda = new Agenda("all", "", idUser, "all");

        mPresenter = new AllAgendaPresenter(this);
        mPresenter.getAllAgendaList(agenda);

        swipeRefreshLayout.setRefreshing(true);
        showShimmer();
    }

    private void initView() {
        toolbarAllAgenda = findViewById(R.id.toolbar_all_agenda);
        setupToolbar();

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container_all_agenda);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutAllAgenda);

        materialSearchView = findViewById(R.id.search_view_all_agenda);
        searchProgressBar = findViewById(R.id.searchProgressBar);

        rvAllAgenda = findViewById(R.id.rvAllAgenda);
        rvAgendaSearch = findViewById(R.id.rvAgendaSearch);
    }

    private void setupAllRecyclerViews() {
        rvAllAgenda.setLayoutManager(new LinearLayoutManager(this));
        rvAgendaSearch.setLayoutManager(new LinearLayoutManager(this));

        allAgendaAdapter = new AllAgendaAdapter(agendas, this);
        rvAllAgenda.setAdapter(allAgendaAdapter);

        agendaSearchAdapter = new AgendaAdapter(agendaSearches, this);
        rvAgendaSearch.setAdapter(agendaSearchAdapter);
    }

    private void showShimmer() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
    }

    private void hideShimmer() {
        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmerAnimation();
    }

    private void setupAnchorSheetBehavior() {
        anchorBehavior = AnchorSheetBehavior.from(findViewById(R.id.anchor_panel));
        anchorBehavior.setHideable(true);
        anchorBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);

        ViewGroup anchorSheet = findViewById(R.id.anchor_panel);
        ViewGroup.LayoutParams params = anchorSheet.getLayoutParams();
        swipeRefreshLayout.post(() -> {
            params.height = swipeRefreshLayout.getHeight();
            anchorSheet.setLayoutParams(params);
        });
        anchorBehavior.setAnchorOffset(0.0f);
    }

    private void setupListenerOrCallback() {

        swipeRefreshLayout.setOnRefreshListener(() -> {
            agendas.clear();
            mPresenter.getAllAgendaList(agenda);
            showShimmer();

            // AgendaAdapter agendaAdapter = (AgendaAdapter) Objects.requireNonNull(rvAgenda.getAdapter());
            allAgendaAdapter.notifyDataSetChanged();
        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                searchProgressBar.setVisibility(View.VISIBLE);
                agendaSearches.clear();
                agendaSearchAdapter.notifyDataSetChanged();
                Search search = new Search(query, "all", "all");
                mPresenter.requestAgendaSearch(search);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                anchorBehavior.setState(AnchorSheetBehavior.STATE_EXPANDED);
                agendaSearches.clear();
                agendaSearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                anchorBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);
            }
        });

        anchorBehavior.setAnchorSheetCallback(new AnchorSheetBehavior.AnchorSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @AnchorSheetBehavior.State int newState) {
                if (newState == AnchorSheetBehavior.STATE_HIDDEN) {
                    materialSearchView.closeSearch();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbarAllAgenda);
        TextView toolbarTitle = toolbarAllAgenda.findViewById(R.id.toolbar_title_all_agenda);
        toolbarTitle.setText(R.string.all_agenda_toolbar_title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void populateAllAgenda(AgendaResponse agendaResponse) {
        new Handler().postDelayed(() -> {

            agendas.addAll(agendaResponse.getData());

            AllAgendaAdapter allAgendaAdapter = (AllAgendaAdapter) Objects.requireNonNull(rvAllAgenda.getAdapter());
            allAgendaAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            hideShimmer();

        }, 1500);
    }

    @Override
    public void showAllAgendaFailure(String message) {
        hideShimmer();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateAgendaSearch(AgendaResponse agendaResponse) {
        new Handler().postDelayed(() -> {
            searchProgressBar.setVisibility(View.GONE);

            if (agendaResponse != null) {
                agendaSearches.clear();
                agendaSearches.addAll(agendaResponse.getData());
                agendaSearchAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    @Override
    public void showAgendaSearchFailure(String message) {
        searchProgressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
