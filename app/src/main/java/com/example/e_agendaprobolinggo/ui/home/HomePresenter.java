package com.example.e_agendaprobolinggo.ui.home;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.request.Search;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;
    private final HomeContract.Interactor mInteractor;

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mInteractor = new HomeInteractor();
    }

    @Override
    public void requestAgendaList(Agenda agenda) {
        mInteractor.requestAgendaList(agenda, new HomeContract.AgendaRequestCallback() {
            @Override
            public void onAgendaRequestCompleted(AgendaResponse agendaResponse) {
                mView.populateAgenda(agendaResponse);
            }

            @Override
            public void onAgendaRequestFailure(String message) {
                // Must in main thread
                mView.showAgendaFailure(message);
            }
        });
    }

    @Override
    public void requestAgendaCategoryList() {
        mInteractor.requestAgendaCategoryList(new HomeContract.AgendaCategoryRequestCallback() {
            @Override
            public void onAgendaCategoryRequestCompleted(KategoriResponse agendaCategories) {
                // Must in main thread
                mView.populateAgendaCategory(agendaCategories);
            }

            @Override
            public void onAgendaCategoryRequestFailure(String message) {
                // Must in main thread
                mView.showAgendaCategoryFailure(message);
            }
        });
    }

    @Override
    public void requestAgendaSearch(Search search) {
        mInteractor.requestAgendaSearch(search, new HomeContract.SearchRequestCallback() {
            @Override
            public void onSearchRequestCompleted(AgendaResponse agendaResponse) {
                mView.populateAgendaSearch(agendaResponse);
            }

            @Override
            public void onSearchRequestFailure(String message) {
                mView.showAgendaSearchFailure(message);
            }
        });
    }
}
