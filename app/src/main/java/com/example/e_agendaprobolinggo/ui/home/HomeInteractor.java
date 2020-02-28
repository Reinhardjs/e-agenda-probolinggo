package com.example.e_agendaprobolinggo.ui.home;

import com.example.e_agendaprobolinggo.model.response.Agenda;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.example.e_agendaprobolinggo.network.NetworkApi;
import com.example.e_agendaprobolinggo.network.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class HomeInteractor implements HomeContract.Interactor {

    private NetworkApi networkApi = UtilsApi.getApiService();
    private Agenda agendaResponse = null;

    @Override
    public void requestAgendaList(HomeContract.AgendaRequestCallback agendaRequestCallback) {

        networkApi.getAgenda().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Agenda>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Agenda agenda) {
                        agendaResponse = agenda;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                        try {
                            JSONObject jsonObject = new JSONObject(errorResponse.string());
                            agendaRequestCallback.onAgendaRequestFailure(jsonObject.getString("message"));
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (agendaResponse != null){
                            if (agendaResponse.isStatus()){
                                agendaRequestCallback.onAgendaRequestCompleted(agendaResponse);
                            } else {
                                agendaRequestCallback.onAgendaRequestFailure(agendaResponse.getMessage());
                            }
                        }
                    }
                });

//        ArrayList<DataAgenda> agendas = new ArrayList<>();
//        DataAgenda dataAgenda = new DataAgenda();
//        dataAgenda.setNamaKegiatan("Mengikuti coaching aplikasi");
//        dataAgenda.setAgenda("Seni Budaya");
//        dataAgenda.setKategori("Bupati");
//        dataAgenda.setCreatedAt("22 Januari 2018");
//        dataAgenda.setStatusKehadiran("Hadir");
//        dataAgenda.setJam("08.00");
//        dataAgenda.setJamend("09.00");
//        agendas.add(dataAgenda);
//
//        dataAgenda = new DataAgenda();
//        dataAgenda.setNamaKegiatan("Pernikahan putra bungsuku");
//        dataAgenda.setAgenda("Pernikahan");
//        dataAgenda.setKategori("Ibu Bupati");
//        dataAgenda.setCreatedAt("12 Februari 2018");
//        dataAgenda.setStatusKehadiran("Diwakilkan");
//        dataAgenda.setJam("07.00");
//        dataAgenda.setJamend("10.00");
//        agendas.add(dataAgenda);
//
//        dataAgenda = new DataAgenda();
//        dataAgenda.setNamaKegiatan("Perayaan 17 Agustus");
//        dataAgenda.setAgenda("Kejuaraan");
//        dataAgenda.setKategori("Asisten Sekda I");
//        dataAgenda.setCreatedAt("18 Agustus 2018");
//        dataAgenda.setStatusKehadiran("Hadir");
//        dataAgenda.setJam("07.00");
//        dataAgenda.setJamend("selesai");
//        agendas.add(dataAgenda);
//
//        Agenda agenda = new Agenda();
//        agenda.setData(agendas);
//
//        if (true) {
//            // Must executed in main thread
//            agendaRequestCallback.onAgendaRequestCompleted(agenda);
//        } else {
//            // Must executed in main thread
//            agendaRequestCallback.onAgendaRequestFailure("Request Agenda Gagal");
//        }

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
