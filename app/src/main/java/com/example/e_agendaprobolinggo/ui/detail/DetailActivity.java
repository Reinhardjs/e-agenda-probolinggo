package com.example.e_agendaprobolinggo.ui.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.google.android.material.card.MaterialCardView;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public static final String KEY = "key";
    private DetailContract.Presenter mPresenter;
    private TextView tvNameAgenda, tvCategoryAgenda, tvPlaceAgenda, tvPersonAgenda, tvStatusAgenda,
    tvStartDate, tvStartTime, tvEndDate, tvEndTime, tvRoundown, tvNote, tvClothes;
    private MaterialCardView materialCardView;
    private String key;
    private Toolbar toolbarDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        key = getIntent().getStringExtra(KEY);

        mPresenter = new DetailPresenter(this);
        mPresenter.getDetailAgenda(key);
    }

    private void initView() {
        toolbarDetail = findViewById(R.id.toolbar_detail);
        setupTollbar();
        tvNameAgenda = findViewById(R.id.tv_name_agenda);
        tvCategoryAgenda = findViewById(R.id.tv_category_agenda);
        tvPlaceAgenda = findViewById(R.id.tv_place_agenda);
        tvPersonAgenda = findViewById(R.id.tv_person_agenda);
        tvStatusAgenda = findViewById(R.id.tv_status);
        tvStartDate = findViewById(R.id.tv_start_date);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndDate = findViewById(R.id.tv_end_date);
        tvEndTime = findViewById(R.id.tv_end_time);
        tvRoundown = findViewById(R.id.tv_roundown);
        tvNote = findViewById(R.id.tv_note);
        tvClothes = findViewById(R.id.tv_clothes);
        materialCardView = findViewById(R.id.cardLabeled);
    }

    private void setupTollbar() {
        setSupportActionBar(toolbarDetail);
        TextView toolbarTitle = toolbarDetail.findViewById(R.id.toolbar_title_detail);
        toolbarTitle.setText(R.string.detail_toolbar_title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void populateDetailAenda(AgendaResponse agendaResponse) {
        DataAgenda dataAgenda = agendaResponse.getData().get(0);
        tvNameAgenda.setText(dataAgenda.getNamaKegiatan());
        tvCategoryAgenda.setText(dataAgenda.getKategori());
        tvPlaceAgenda.setText(dataAgenda.getTempat());
        tvPersonAgenda.setText(dataAgenda.getSubAgenda());
        tvStartDate.setText(dataAgenda.getTanggal());
        tvEndDate.setText(dataAgenda.getTanggalend());
        tvStartTime.setText(dataAgenda.getJam());
        tvEndTime.setText(dataAgenda.getJamend());
        tvStatusAgenda.setText(dataAgenda.getStatusAgenda());
        tvRoundown.setText(Html.fromHtml(dataAgenda.getUrutanAcara()));
        tvNote.setText(Html.fromHtml(dataAgenda.getCatatan()));
        tvClothes.setText(dataAgenda.getPakaian());
        materialCardView.setCardBackgroundColor(Color.parseColor(agendaResponse.getData().get(0).getStatusColor()));
    }

    @Override
    public void showFailureDetailAgenda(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
}
