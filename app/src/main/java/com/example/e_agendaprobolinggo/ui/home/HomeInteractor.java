package com.example.e_agendaprobolinggo.ui.home;

import android.util.SparseArray;

import com.example.e_agendaprobolinggo.model.body.AgendaRequest;
import com.example.e_agendaprobolinggo.model.body.AgendaType;
import com.example.e_agendaprobolinggo.model.body.SubAgendaType;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;
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
    private AgendaResponse agendaResponse = null;
    private KategoriResponse kategoriResponse = null;

    @Override
    public void requestAgendaList(HomeContract.AgendaRequestCallback agendaRequestCallback) {

        AgendaRequest agendaRequest = new AgendaRequest("all", "5", "all");
        networkApi.getAgenda(agendaRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AgendaResponse agendaRes) {
                        agendaResponse = agendaRes;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
//                            if (((HttpException) e).code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                                ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                                try {
                                    JSONObject jsonObject = new JSONObject(errorResponse.string());
                                    agendaRequestCallback.onAgendaRequestFailure(jsonObject.getString("message"));
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
//
                        }

                    }

                    @Override
                    public void onComplete() {
                        if (agendaResponse != null) {
                            if (agendaResponse.isStatus()) {
                                agendaRequestCallback.onAgendaRequestCompleted(agendaResponse);
                            } else {
                                agendaRequestCallback.onAgendaRequestFailure(agendaResponse.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void requestAgendaTypeList(HomeContract.AgendaTypeRequestCallback agendaTypeRequestCallback) {

        networkApi.getKategory().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KategoriResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull KategoriResponse kategoriRes) {
                        kategoriResponse = kategoriRes;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                agendaTypeRequestCallback.onAgendaTypeRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (kategoriResponse != null){
                            if (kategoriResponse.isStatus()){
                                agendaTypeRequestCallback.onAgendaTypeRequestCompleted(kategoriResponse);
                            } else {
                                agendaTypeRequestCallback.onAgendaTypeRequestFailure(kategoriResponse.getMessage());
                            }
                        }
                    }
                });

//        ArrayList<AgendaType> agendaTypes = new ArrayList<>();
//
//
//        // ########################################################################
//        AgendaType bupati = new AgendaType();
//        bupati.setIdAgenda("1");
//        bupati.setAgendaName("Bupati");
//
//        SparseArray<SubAgendaType> bupati_sub_agendas = new SparseArray<>();
//        bupati_sub_agendas.put(0, new SubAgendaType("1", "Bapak Bupati"));
//        bupati_sub_agendas.put(1, new SubAgendaType("2", "Ibu Bupati"));
//        bupati_sub_agendas.put(2, new SubAgendaType("3", "Bapak Ibu Bupati"));
//        bupati.setSubAgendaList(bupati_sub_agendas);
//        // ########################################################################
//
//
//        // ########################################################################
//        AgendaType wakil_bupati = new AgendaType();
//        wakil_bupati.setIdAgenda("2");
//        wakil_bupati.setAgendaName("Wakil Bupati");
//
//        SparseArray<SubAgendaType> wakil_bupati_subagendas = new SparseArray<>();
//        wakil_bupati_subagendas.put(0, new SubAgendaType("4", "Bapak Wakil Bupati"));
//        wakil_bupati_subagendas.put(1, new SubAgendaType("5", "Ibu Wakil Bupati"));
//        wakil_bupati_subagendas.put(2, new SubAgendaType("6", "Bapak Ibu Wakil Bupati"));
//        wakil_bupati.setSubAgendaList(wakil_bupati_subagendas);
//        // ########################################################################
//
//
//        // ########################################################################
//        AgendaType sekretaris_daerah = new AgendaType();
//        sekretaris_daerah.setIdAgenda("3");
//        sekretaris_daerah.setAgendaName("Sekda");
//
//        SparseArray<SubAgendaType> sekretaris_daerah_subagendas = new SparseArray<>();
//        sekretaris_daerah_subagendas.put(0, new SubAgendaType("7", "Sekda"));
//        sekretaris_daerah.setSubAgendaList(sekretaris_daerah_subagendas);
//        // ########################################################################
//
//
//        // ########################################################################
//        AgendaType ass1_sekda = new AgendaType();
//        ass1_sekda.setIdAgenda("4");
//        ass1_sekda.setAgendaName("Asisten I Sekda");
//
//        SparseArray<SubAgendaType> ass1_sekda_subagendas = new SparseArray<>();
//        ass1_sekda_subagendas.put(0, new SubAgendaType("8", "Asisten I Sekda"));
//        ass1_sekda.setSubAgendaList(ass1_sekda_subagendas);
//        // ########################################################################
//
//
//        // ########################################################################
//        AgendaType ass2_sekda = new AgendaType();
//        ass2_sekda.setIdAgenda("5");
//        ass2_sekda.setAgendaName("Asisten II Sekda");
//
//        SparseArray<SubAgendaType> ass2_sekda_subagendas = new SparseArray<>();
//        ass2_sekda_subagendas.put(0, new SubAgendaType("9", "Asisten II Sekda"));
//        ass2_sekda.setSubAgendaList(ass2_sekda_subagendas);
//        // ########################################################################
//
//
//        // ########################################################################
//        AgendaType ass3_sekda = new AgendaType();
//        ass3_sekda.setIdAgenda("6");
//        ass3_sekda.setAgendaName("Asisten III Sekda");
//
//        SparseArray<SubAgendaType> ass3_sekda_subagendas = new SparseArray<>();
//        ass3_sekda_subagendas.put(0, new SubAgendaType("10", "Asisten III Sekda"));
//        ass3_sekda.setSubAgendaList(ass3_sekda_subagendas);
//        // ########################################################################
//
//
//        // ########################################################################
//        AgendaType protokol = new AgendaType();
//        protokol.setIdAgenda("7");
//        protokol.setAgendaName("Protokol");
//
//        SparseArray<SubAgendaType> protokol_subagendas = new SparseArray<>();
//        protokol_subagendas.put(0, new SubAgendaType("11", "Protokol"));
//        protokol.setSubAgendaList(protokol_subagendas);
//        // ########################################################################
//
//
//        agendaTypes.add(bupati);
//        agendaTypes.add(wakil_bupati);
//        agendaTypes.add(sekretaris_daerah);
//        agendaTypes.add(ass1_sekda);
//        agendaTypes.add(ass2_sekda);
//        agendaTypes.add(ass3_sekda);
//        agendaTypes.add(protokol);
//
//
//        if (true) {
//            // Must executed in main thread
//            agendaTypeRequestCallback.onAgendaTypeRequestCompleted(agendaTypes);
//        } else {
//            // Must executed in main thread
//            agendaTypeRequestCallback.onAgendaTypeRequestFailure("Request Category Gagal");
//        }
    }
}
