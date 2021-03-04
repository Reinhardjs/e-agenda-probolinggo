package com.example.e_agendaprobolinggo.ui.category;

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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.model.response.User;
import com.example.e_agendaprobolinggo.ui.home.AgendaAdapter;
import com.example.e_agendaprobolinggo.ui.home.customsearchutils.AnchorSheetBehavior;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {

    public static final String CATEGORY_ID = "category_id";
    public static final String SUB_CATEGORY_ID = "sub_category_id";
    public static final String SUB_CATEGORY_NAME = "sub_category_name";

    ArrayList<DataAgenda> agendas = new ArrayList<>();
    ArrayList<DataAgenda> agendaSearches = new ArrayList<>();

    private RecyclerView rvAgendaPerCategory, rvAgendaSearch;

    private AgendaAdapter agendaSearchAdapter;
    private AgendaPerCategoryAdapter agendaPerCategoryAdapter;

    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;

    private String categoryId, subCategoryId, subCategoryName;
    private CategoryContract.Presenter mPresenter;

    private MaterialSearchView materialSearchView;
    private AnchorSheetBehavior<View> anchorBehavior;

    private ProgressBar searchProgressBar;

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
        setContentView(R.layout.activity_category);

        // Ini untuk mengatasi masalah result anchorsheet yang ketutup toolbar pas keyboard muncul
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        categoryId = getIntent().getStringExtra(CATEGORY_ID);
        subCategoryId = getIntent().getStringExtra(SUB_CATEGORY_ID);
        subCategoryName = getIntent().getStringExtra(SUB_CATEGORY_NAME);

        mPresenter = new CategoryPresenter(this);

        initView();
        addListener();

        User user = SharedPreferenceUtils.getUser(this);
        String idUser = user.getId();

        agenda = new Agenda(categoryId, "", idUser, subCategoryId);

        mPresenter.getCategoryAgendaList(agenda);

        swipeRefreshLayout.setRefreshing(true);
        showShimmer();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();

        searchProgressBar = findViewById(R.id.searchProgressBar);

        rvAgendaPerCategory = findViewById(R.id.rvAgendaPerCategory);
        rvAgendaSearch = findViewById(R.id.rvAgendaSearch);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutCategory);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_category);

        rvAgendaSearch.setLayoutManager(new LinearLayoutManager(this));
        rvAgendaPerCategory.setLayoutManager(new LinearLayoutManager(this));

        agendaSearchAdapter = new AgendaAdapter(agendaSearches, this);
        rvAgendaSearch.setAdapter(agendaSearchAdapter);

        agendaPerCategoryAdapter = new AgendaPerCategoryAdapter(agendas, this);
        rvAgendaPerCategory.setAdapter(agendaPerCategoryAdapter);

        materialSearchView = findViewById(R.id.search_view);
        setupAnchorSheetBehavior();
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

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(subCategoryName);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void addListener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            agendas.clear();
            mPresenter.getCategoryAgendaList(agenda);
            showShimmer();

            agendaPerCategoryAdapter.notifyDataSetChanged();
        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                searchProgressBar.setVisibility(View.VISIBLE);
                agendaSearches.clear();
                agendaSearchAdapter.notifyDataSetChanged();
                mPresenter.getAgendaPerCategorySearch(query, categoryId, subCategoryId);
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

    @Override
    public void populateAgendaPerCategorySearch(AgendaResponse agendaResponse) {
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
    public void showAgendaPerCategorySearchFailure(String message) {
        searchProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
