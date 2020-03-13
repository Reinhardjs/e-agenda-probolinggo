package com.example.e_agendaprobolinggo.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.body.User;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.model.response.DataKategori;
import com.example.e_agendaprobolinggo.model.response.DataSubKategori;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;
import com.example.e_agendaprobolinggo.ui.category.CategoryActivity;
import com.example.e_agendaprobolinggo.ui.home.customsearchutils.AnchorSheetBehavior;
import com.example.e_agendaprobolinggo.utils.AppDimenUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    ArrayList<DataAgenda> agendas = new ArrayList<>();
    ArrayList<DataKategori> agendaTypes = new ArrayList<>();
    ArrayList<DataAgenda> agendaSearches = new ArrayList<>();

    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeContract.Presenter mPresenter;
    private RecyclerView rvAgenda, rvAgendaType, rvAgendaSearch;
    private AgendaAdapter agendaAdapter;
    private AgendaAdapter agendaSearchAdapter;
    private AgendaTypeAdapter agendaTypeAdapter;

    private TextView tvSeeAll, tvWelcome;

    private Toolbar toolbar;

    private MaterialSearchView materialSearchView;
    private AnchorSheetBehavior<View> anchorBehavior;
    private ProgressBar searchProgressBar;

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
        getMenuInflater().inflate(R.menu.home_menu_item, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ini untuk mengatasi masalah result anchorsheet yang ketutup toolbar pas keyboard muncul
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        initView();
        setupListenerOrCallback();

        mPresenter = new HomePresenter(this);
        mPresenter.requestAgendaTypeList();
        mPresenter.requestAgendaList();

        swipeRefreshLayout.setRefreshing(true);
        showShimmer();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();

        int[] location = new int[2];
        toolbar.getLocationOnScreen(location);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        searchProgressBar = findViewById(R.id.searchProgressBar);

        rvAgenda = findViewById(R.id.rvAgenda);
        rvAgendaSearch = findViewById(R.id.rvAgendaSearch);
        rvAgendaType = findViewById(R.id.rvAgendaType);

        tvSeeAll = findViewById(R.id.tvSeeAll);
        tvWelcome = findViewById(R.id.tvWelcome);
        materialSearchView = findViewById(R.id.search_view);

        User user = SharedPreferenceUtils.getUser(this);
        tvWelcome.setText(Html.fromHtml("Selamat datang, <b>" + user.getNama() + "</b>"));
        setupAllRecyclerViews();
        setupAnchorSheetBehavior();
    }

    private void setupAnchorSheetBehavior() {
        anchorBehavior = AnchorSheetBehavior.from(findViewById(R.id.anchor_panel));
        anchorBehavior.setHideable(true);
        anchorBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);

        ViewGroup anchorSheet = findViewById(R.id.anchor_panel);
        ViewGroup.LayoutParams params = anchorSheet.getLayoutParams();
        params.height = Resources.getSystem().getDisplayMetrics().heightPixels - AppDimenUtil.getActionBarHeight(this);
        anchorSheet.setLayoutParams(params);

        anchorBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels - AppDimenUtil.getActionBarHeight(this));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("E-Agenda Probolinggo");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void setupAllRecyclerViews() {
        rvAgenda.setLayoutManager(new LinearLayoutManager(this));
        rvAgendaSearch.setLayoutManager(new LinearLayoutManager(this));
        rvAgendaType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        agendaAdapter = new AgendaAdapter(agendas);
        rvAgenda.setAdapter(agendaAdapter);

        agendaSearchAdapter = new AgendaAdapter(agendaSearches);
        rvAgendaSearch.setAdapter(agendaSearchAdapter);
        //rvAgendaSearch.setAdapter(agendaAdapter);

        agendaTypeAdapter = new AgendaTypeAdapter(agendaTypes);
        rvAgendaType.setAdapter(agendaTypeAdapter);
    }

    private void setupListenerOrCallback() {

        swipeRefreshLayout.setOnRefreshListener(() -> {
            agendas.clear();
            mPresenter.requestAgendaList();
            showShimmer();

            // AgendaAdapter agendaAdapter = (AgendaAdapter) Objects.requireNonNull(rvAgenda.getAdapter());
            agendaAdapter.notifyDataSetChanged();
        });

        agendaTypeAdapter.setOnClickAgendaTypeCallback(agendaType -> {

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(HomeActivity.this);
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.select_dialog_item);

            List<DataSubKategori> subAgendas = agendaType;

            for (int i = 0; i < subAgendas.size(); i++) {
                String subAgendaName = subAgendas.get(i).getSubRole();
                arrayAdapter.add(subAgendaName);
            }

            // builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());
            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                String agendaId = agendaType.get(which).getIdRole2();
                String subAgendaId = agendaType.get(which).getIdSubRole();

                Intent intentPerCategory = new Intent(HomeActivity.this, CategoryActivity.class);
                intentPerCategory.putExtra(CategoryActivity.AGENDA_ID, agendaId);
                intentPerCategory.putExtra(CategoryActivity.SUB_AGENDA_ID, subAgendaId);
                startActivity(intentPerCategory);
            });
            builderSingle.show();

        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                searchProgressBar.setVisibility(View.VISIBLE);
                mPresenter.requestAgendaSearch(query);
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
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                anchorBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);
                rvAgendaSearch.setAdapter(null);
            }
        });

        anchorBehavior.setAnchorSheetCallback(new AnchorSheetBehavior.AnchorSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @AnchorSheetBehavior.State int newState) {
                if (newState == AnchorSheetBehavior.STATE_HIDDEN){
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
    public void populateAgenda(AgendaResponse agendaResponse) {
        new Handler().postDelayed(() -> {
            hideShimmer();
            agendas.addAll(agendaResponse.getData());

            // AgendaAdapter agendaAdapter = (AgendaAdapter) Objects.requireNonNull(rvAgenda.getAdapter());
            agendaAdapter.notifyDataSetChanged();

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override
    public void showAgendaFailure(String message) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateAgendaType(KategoriResponse agendaTypes) {
        this.agendaTypes.addAll(agendaTypes.getData());

        agendaTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAgendaTypeFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateAgendaSearch(AgendaResponse agendaResponse) {
        new Handler().postDelayed(() -> {
            searchProgressBar.setVisibility(View.GONE);

            if (agendaResponse != null) {
                agendaSearches.addAll(agendaResponse.getData());
                agendaSearchAdapter.notifyDataSetChanged();
            }
        }, 1500);
    }

    @Override
    public void showAgendaSearchFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
