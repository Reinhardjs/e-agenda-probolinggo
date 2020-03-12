package com.example.e_agendaprobolinggo.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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
import com.example.e_agendaprobolinggo.model.body.AgendaRequest;
import com.example.e_agendaprobolinggo.model.body.AgendaType;
import com.example.e_agendaprobolinggo.model.body.SubAgendaType;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.model.response.DataKategori;
import com.example.e_agendaprobolinggo.model.response.DataSubKategori;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;
import com.example.e_agendaprobolinggo.ui.category.CategoryActivity;
import com.example.e_agendaprobolinggo.ui.home.customsearchutils.SearchResultDialogFragment;
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
    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeContract.Presenter mPresenter;
    private RecyclerView rvAgendaType, rvAgenda;
    private AgendaAdapter agendaAdapter;
    private AgendaTypeAdapter agendaTypeAdapter;
    private TextView tvSeeAll;
    private Toolbar toolbar;
    private MaterialSearchView materialSearchView;
    private AnchorSheetBehavior<View> anchorBehavior;

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

        mPresenter = new HomePresenter(this);

        initView();
        addListener();

        swipeRefreshLayout.setRefreshing(true);
        mPresenter.requestAgendaTypeList();
        mPresenter.requestAgendaList();
        showShimmer();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("E-Agenda");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        rvAgendaType = findViewById(R.id.rvAgendaType);
        rvAgenda = findViewById(R.id.rvAgenda);
        tvSeeAll = findViewById(R.id.tvSeeAll);
        materialSearchView = findViewById(R.id.search_view);

        rvAgendaType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvAgenda.setLayoutManager(new LinearLayoutManager(this));

        agendaTypeAdapter = new AgendaTypeAdapter(agendaTypes);
        rvAgendaType.setAdapter(agendaTypeAdapter);

        agendaAdapter = new AgendaAdapter(agendas);
        rvAgenda.setAdapter(agendaAdapter);

        anchorBehavior = AnchorSheetBehavior.from(findViewById(R.id.anchor_panel));
        anchorBehavior.setHideable(true);
        anchorBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);

        ViewGroup anchorSheet = findViewById(R.id.anchor_panel);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) anchorSheet.getLayoutParams();
        params.height = Resources.getSystem().getDisplayMetrics().heightPixels - AppDimenUtil.getActionBarHeight(this);
        anchorSheet.setLayoutParams(params);

        anchorBehavior.setAnchorOffset(AppDimenUtil.getActionBarHeight(getApplicationContext()));
        anchorBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels - AppDimenUtil.getActionBarHeight(this));
    }

    private void addListener() {

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

//            agendaType
            List<DataSubKategori> subAgendas = agendaType;
//            SparseArray<String> whichSubSparse = new SparseArray<>();

            for (int i = 0; i < subAgendas.size(); i++) {
//                int key = subAgendas.keyAt(i);
//                SubAgendaType subAgendaType = subAgendas.get(key);
                String subAgendaName = subAgendas.get(i).getSubRole();

//                Log.d("MYAPP", subAgendaType.getIdSubAgenda());
//                String subAgendaName = subAgendaType.getSubAgendaName();
                arrayAdapter.add(subAgendaName);

//                String subAgendaId = subAgendaType.getIdSubAgenda();
//                whichSubSparse.append(whichSubSparse.size() - 1, subAgendaId);
            }

            // builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());
            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                String agendaId = agendaType.get(which).getIdRole2();
                String subAgendaId = agendaType.get(which).getIdSubRole();

//                Toast.makeText(getApplicationContext(), "AGENDA ID : " + agendaId + "\n" + "SUB AGENDA ID : " + subAgendaId, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "query : " + query, Toast.LENGTH_SHORT).show();
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
            }
        });

        anchorBehavior.setAnchorSheetCallback(new AnchorSheetBehavior.AnchorSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @AnchorSheetBehavior.State int newState) {

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

            AgendaAdapter agendaAdapter = (AgendaAdapter) Objects.requireNonNull(rvAgenda.getAdapter());
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
}
