package com.example.e_agendaprobolinggo.ui.home;

import com.example.e_agendaprobolinggo.model.response.Agenda;

import java.util.ArrayList;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private HomeContract.Interactor mInteractor;

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mInteractor = new HomeInteractor();
    }

    @Override
    public void getAgendaList() {
        mInteractor.requestAgendaList(new HomeContract.AgendaRequestCallback() {
            @Override
            public void onAgendaRequestCompleted(Agenda agenda) {
                mView.populateAgenda(agenda);
            }

            @Override
            public void onAgendaRequestFailure(String message) {
                // Must in main thread
                mView.showAgendaFailure(message);
            }
        });
    }

    @Override
    public void getCategoryList() {
        mInteractor.requestCategoryList(new HomeContract.CategoryRequestCallback() {
            @Override
            public void onCategoryRequestCompleted(ArrayList<String> categories) {
                // Must in main thread
                mView.populateCategory(categories);
            }

            @Override
            public void onCategoryRequestFailure(String message) {
                // Must in main thread
                mView.showCategoryFailure(message);
            }
        });
    }
}
