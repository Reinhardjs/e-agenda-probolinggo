package com.example.e_agendaprobolinggo.ui.category;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.request.Search;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public class CategoryPresenter implements CategoryContract.Presenter {

    private final CategoryContract.View mView;
    private final CategoryContract.Interactor mInteractor;

    public CategoryPresenter(CategoryContract.View view) {
        mView = view;
        mInteractor = new CategoryInteractor();
    }

    @Override
    public void getCategoryAgendaList(Agenda agenda) {
        mInteractor.requestAgendaList(agenda, new CategoryContract.CategoryAgendaRequestCallback() {
            @Override
            public void onCategoryAgendaRequestCompleted(AgendaResponse agendaResponse) {
                mView.populateCategoryAgenda(agendaResponse);
            }

            @Override
            public void onCategoryAgendaRequestFailure(String message) {
                mView.showCategoryAgendaFailure(message);
            }

        });
    }

    @Override
    public void getAgendaPerCategorySearch(Search search) {
        mInteractor.requestAgendaPerCategorySearch(search, new CategoryContract.AgendaPerCategorySearchRequestCallBack() {
            @Override
            public void onAgendaPerCategorySearchRequestCompleted(AgendaResponse agendaResponse) {
                mView.populateAgendaPerCategorySearch(agendaResponse);
            }

            @Override
            public void onAgendaPerCategorySearchRequestFailure(String message) {
                mView.showAgendaPerCategorySearchFailure(message);
            }
        });
    }


}
