package com.example.e_agendaprobolinggo.ui.home;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private HomeContract.Interactor mInteractor;

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mInteractor = new HomeInteractor();
    }

    @Override
    public void requestAgendaList() {
        mInteractor.requestAgendaList(new HomeContract.AgendaRequestCallback() {
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
    public void requestAgendaTypeList() {
        mInteractor.requestAgendaTypeList(new HomeContract.AgendaTypeRequestCallback() {
            @Override
            public void onAgendaTypeRequestCompleted(KategoriResponse agendaTypes) {
                // Must in main thread
                mView.populateAgendaType(agendaTypes);
            }

            @Override
            public void onAgendaTypeRequestFailure(String message) {
                // Must in main thread
                mView.showAgendaTypeFailure(message);
            }
        });
    }

    @Override
    public void requestAgendaSearch(String keyword) {
        mInteractor.requestAgendaSearch(keyword, new HomeContract.SearchRequestCallback() {
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
