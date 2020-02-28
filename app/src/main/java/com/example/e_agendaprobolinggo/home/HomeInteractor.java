package com.example.e_agendaprobolinggo.home;

import com.example.e_agendaprobolinggo.model.response.Agenda;
import com.example.e_agendaprobolinggo.model.response.DataItem;

import java.util.ArrayList;

public class HomeInteractor implements HomeContract.Interactor {

    @Override
    public void requestAgendaList(HomeContract.AgendaRequestCallback agendaRequestCallback) {

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

        Agenda agenda = new Agenda();
        agenda.setData(agendas);

        if (true) {
            // Must executed in main thread
            agendaRequestCallback.onAgendaRequestCompleted(agenda);
        } else {
            // Must executed in main thread
            agendaRequestCallback.onAgendaRequestFailure("Request Agenda Gagal");
        }
    }

    @Override
    public void requestCategoryList(HomeContract.CategoryRequestCallback categoryRequestCallback) {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Bupati");
        categories.add("Wakil Bupati");
        categories.add("Sekda");
        categories.add("Asisten Sekda I");

        if (true) {
            // Must executed in main thread
            categoryRequestCallback.onCategoryRequestCompleted(categories);
        } else {
            // Must executed in main thread
            categoryRequestCallback.onCategoryRequestFailure("Request Category Gagal");
        }
    }
}
