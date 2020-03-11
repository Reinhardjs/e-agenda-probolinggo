package com.example.e_agendaprobolinggo.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.body.AgendaType;
import com.example.e_agendaprobolinggo.model.body.SubAgendaType;
import com.example.e_agendaprobolinggo.model.response.Agenda;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.ui.home.customsearchresult.SearchResultDialogFragment;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    ArrayList<DataAgenda> agendas = new ArrayList<>();
    ArrayList<AgendaType> agendaTypes = new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeContract.Presenter mPresenter;
    private RecyclerView rvAgendaType, rvAgenda;
    private AgendaAdapter agendaAdapter;
    private AgendaTypeAdapter agendaTypeAdapter;
    private TextView tvSeeAll;
    private Toolbar toolbar;
    private MaterialSearchView materialSearchView;
    private SearchResultDialogFragment searchResultDialogFragment;

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

        mPresenter = new HomePresenter(this);

        initView();
        addListener();

        swipeRefreshLayout.setRefreshing(true);
        mPresenter.requestAgendaTypeList();
        mPresenter.requestAgendaList();
        showShimmer();

        searchResultDialogFragment = new SearchResultDialogFragment();
        searchResultDialogFragment.show(getSupportFragmentManager(), searchResultDialogFragment.getTag());
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

            SparseArray<SubAgendaType> subAgendas = agendaType.getSubAgendaList();
            SparseArray<String> whichSubSparse = new SparseArray<>();

            for (int i = 0; i < subAgendas.size(); i++) {
                int key = subAgendas.keyAt(i);
                SubAgendaType subAgendaType = subAgendas.get(key);

                Log.d("MYAPP", subAgendaType.getIdSubAgenda());
                String subAgendaName = subAgendaType.getSubAgendaName();
                arrayAdapter.add(subAgendaName);

                String subAgendaId = subAgendaType.getIdSubAgenda();
                whichSubSparse.append(whichSubSparse.size() - 1, subAgendaId);
            }

            // builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());
            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                String agendaId = agendaType.getIdAgenda();
                String subAgendaId = whichSubSparse.valueAt(which);

                Toast.makeText(getApplicationContext(), "AGENDA ID : " + agendaId + "\n" + "SUB AGENDA ID : " + subAgendaId, Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
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
    public void populateAgenda(Agenda agenda) {
        new Handler().postDelayed(() -> {
            hideShimmer();
            agendas.addAll(agenda.getData());

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
    public void populateAgendaType(ArrayList<AgendaType> agendaTypes) {
        this.agendaTypes.addAll(agendaTypes);
        agendaTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAgendaTypeFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
