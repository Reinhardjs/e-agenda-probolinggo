package com.example.e_agendaprobolinggo.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.connection.ConnectionLiveData;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.ConnectionModel;
import com.example.e_agendaprobolinggo.model.body.User;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.model.response.DataKategori;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;
import com.example.e_agendaprobolinggo.ui.all_agenda.AllAgendaActivity;
import com.example.e_agendaprobolinggo.ui.category.CategoryActivity;
import com.example.e_agendaprobolinggo.ui.home.customsearchutils.AnchorSheetBehavior;
import com.example.e_agendaprobolinggo.utils.AppDimenUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.e_agendaprobolinggo.connection.ConnectionLiveData.MobileData;
import static com.example.e_agendaprobolinggo.connection.ConnectionLiveData.WifiData;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    ArrayList<DataAgenda> agendas = new ArrayList<>();
    ArrayList<DataKategori> agendaCategories = new ArrayList<>();
    ArrayList<DataAgenda> agendaSearches = new ArrayList<>();

    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvAgenda, rvAgendaCategory, rvAgendaSearch;
    private AgendaAdapter agendaAdapter;
    private AgendaAdapter agendaSearchAdapter;
    private AgendaCategoryAdapter agendaCategoryAdapter;

    private Toolbar toolbar;
    private TextView tvSeeAll, tvWelcome;

    private MaterialSearchView materialSearchView;
    private AnchorSheetBehavior<View> anchorBehavior;

    private ProgressBar searchProgressBar;
    private ImageView ivNoConnection;

    private HomeContract.Presenter mPresenter;

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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.action_logout) {
            SharedPreferenceUtils.removeUser(getApplicationContext());
            Intent i = getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ini untuk mengatasi masalah result anchorsheet yang ketutup toolbar pas keyboard muncul
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        initView();
        setupListenerOrCallback();
        setupInternetObserver();

        mPresenter = new HomePresenter(this);
        mPresenter.requestAgendaCategoryList();
        mPresenter.requestAgendaList();

        swipeRefreshLayout.setRefreshing(true);
        showShimmer();

        tvSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AllAgendaActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        searchProgressBar = findViewById(R.id.searchProgressBar);

        rvAgenda = findViewById(R.id.rvAgenda);
        rvAgendaSearch = findViewById(R.id.rvAgendaSearch);
        rvAgendaCategory = findViewById(R.id.rvAgendaType);

        tvSeeAll = findViewById(R.id.tvSeeAll);
        tvWelcome = findViewById(R.id.tvWelcome);
        materialSearchView = findViewById(R.id.search_view);

        ivNoConnection = findViewById(R.id.ivNoConnection);

        User user = SharedPreferenceUtils.getUser(this);
        tvWelcome.setText(Html.fromHtml("Selamat datang, <b>" + user.getNama() + "</b>"));
        setupAllRecyclerViews();
        setupAnchorSheetBehavior();
    }

    private void setupAnchorSheetBehavior() {
        anchorBehavior = AnchorSheetBehavior.from(findViewById(R.id.anchor_panel));
        anchorBehavior.setHideable(true);
        anchorBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;

        ViewGroup anchorSheet = findViewById(R.id.anchor_panel);
        ViewGroup.LayoutParams params = anchorSheet.getLayoutParams();
        // params.height = height - AppDimenUtil.getActionBarHeight(this) - AppDimenUtil.getStatusbarHeight(this);
        swipeRefreshLayout.post(() -> {
            params.height = swipeRefreshLayout.getHeight() - AppDimenUtil.getStatusbarHeight(this);;
            anchorSheet.setLayoutParams(params);
        });
        anchorBehavior.setAnchorOffset(0.0f);
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
        rvAgendaCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        agendaAdapter = new AgendaAdapter(agendas, this);
        rvAgenda.setAdapter(agendaAdapter);

        agendaSearchAdapter = new AgendaAdapter(agendaSearches, this);
        rvAgendaSearch.setAdapter(agendaSearchAdapter);

        agendaCategoryAdapter = new AgendaCategoryAdapter(agendaCategories, this);
        rvAgendaCategory.setAdapter(agendaCategoryAdapter);
    }

    private void setupInternetObserver() {
        /* Live data object and setting an oberser on it */
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                /* every time connection state changes, we'll be notified and can perform action accordingly */
                if (connection.getIsConnected()) {
                    ivNoConnection.setVisibility(View.GONE);

                    switch (connection.getType()) {
                        case WifiData:
                            // Toast.makeText(HomeActivity.this, String.format("Wifi turned ON"), Toast.LENGTH_SHORT).show();
                            break;
                        case MobileData:
                            // Toast.makeText(HomeActivity.this, String.format("Mobile data turned ON"), Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    ivNoConnection.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void setupListenerOrCallback() {

        swipeRefreshLayout.setOnRefreshListener(() -> {
            agendas.clear();
            mPresenter.requestAgendaList();
            showShimmer();

            // AgendaAdapter agendaAdapter = (AgendaAdapter) Objects.requireNonNull(rvAgenda.getAdapter());
            agendaAdapter.notifyDataSetChanged();
        });

        agendaCategoryAdapter.setOnClickAgendaCategoryCallback(agendaCategories -> {

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(HomeActivity.this);
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.select_dialog_item);

            for (int i = 0; i < agendaCategories.size(); i++) {
                String subAgendaName = agendaCategories.get(i).getSubRole();
                arrayAdapter.add(subAgendaName);
            }

            // builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());
            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                String agendaId = agendaCategories.get(which).getIdRole2();
                String subAgendaId = agendaCategories.get(which).getIdSubRole();
                String subAgendaName = agendaCategories.get(which).getSubRole();
                String subRole = agendaCategories.get(which).getSubRole();


                Intent intentPerCategory = new Intent(HomeActivity.this, CategoryActivity.class);
                intentPerCategory.putExtra(CategoryActivity.AGENDA_ID, agendaId);
                intentPerCategory.putExtra(CategoryActivity.SUB_AGENDA_ID, subAgendaId);
                intentPerCategory.putExtra(CategoryActivity.SUB_AGENDA_NAME, subAgendaName);

                intentPerCategory.putExtra(CategoryActivity.AGENDA, subRole);

                startActivity(intentPerCategory);
            });
            builderSingle.show();

        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                searchProgressBar.setVisibility(View.VISIBLE);
                agendaSearches.clear();
                agendaSearchAdapter.notifyDataSetChanged();
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
    public void populateAgendaCategory(KategoriResponse agendaCategories) {
        new Handler().postDelayed(() -> {
            this.agendaCategories.addAll(agendaCategories.getData());
            agendaCategoryAdapter.notifyDataSetChanged();
        }, 1000);
    }

    @Override
    public void showAgendaCategoryFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
