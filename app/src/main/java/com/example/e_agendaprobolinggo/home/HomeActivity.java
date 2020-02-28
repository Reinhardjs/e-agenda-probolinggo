package com.example.e_agendaprobolinggo.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.DataItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private HomeContract.Presenter mPresenter;
    private RecyclerView rvCategory, rvAgenda;
    private TextView tvSeeAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

        mPresenter = new HomePresenter(this);

        ArrayList<String> categories = populateCategories();
        ArrayList<DataItem> agendas = populateAgendas();

        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(new CategoryAdapter(categories));

        rvAgenda.setLayoutManager(new LinearLayoutManager(this));
        rvAgenda.setAdapter(new AgendaAdapter(agendas));
    }

    private void initView(){
        rvCategory = findViewById(R.id.rvCategory);
        rvAgenda = findViewById(R.id.rvAgenda);
        tvSeeAll = findViewById(R.id.tvSeeAll);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("E-Agenda");

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private ArrayList<String> populateCategories() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Bupati");
        categories.add("Wakil Bupati");
        categories.add("Sekda");
        categories.add("Asisten Sekda I");
        return categories;
    }

    private ArrayList<DataItem> populateAgendas() {
        ArrayList<DataItem> agendas = new ArrayList<>();
        DataItem dataItem = new DataItem();
        dataItem.setNamaKegiatan("Mengikuti coaching aplikasi");
        dataItem.setAgenda("Seni Budaya");
        dataItem.setKategori("Bupati");
        dataItem.setCreatedAt("22 Januari 2018");
        dataItem.setStatusKehadiran("Hadir");
        dataItem.setJam("08.00");
        dataItem.setJamend("09.00");
        agendas.add(dataItem);

        dataItem = new DataItem();
        dataItem.setNamaKegiatan("Pernikahan putra bungsuku");
        dataItem.setAgenda("Pernikahan");
        dataItem.setKategori("Ibu Bupati");
        dataItem.setCreatedAt("12 Februari 2018");
        dataItem.setStatusKehadiran("Diwakilkan");
        dataItem.setJam("07.00");
        dataItem.setJamend("10.00");
        agendas.add(dataItem);

        dataItem = new DataItem();
        dataItem.setNamaKegiatan("Perayaan 17 Agustus");
        dataItem.setAgenda("Kejuaraan");
        dataItem.setKategori("Asisten Sekda I");
        dataItem.setCreatedAt("18 Agustus 2018");
        dataItem.setStatusKehadiran("Hadir");
        dataItem.setJam("07.00");
        dataItem.setJamend("selesai");
        agendas.add(dataItem);
        return agendas;
    }
}
