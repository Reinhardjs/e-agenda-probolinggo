package com.example.e_agendaprobolinggo.ui.category;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View mView;
    private CategoryContract.Interactor mInteractor;

    public CategoryPresenter(CategoryContract.View view){
        mView = view;
        mInteractor = new CategoryInteractor();
    }

    @Override
    public void getCategoryAgendaList(String agendaId, String subAgendaId) {
        mInteractor.requestAgendaList(agendaId, subAgendaId, new CategoryContract.CategoryAgendaRequestCallback() {
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
    public void getAgendaPerCategorySearch(String keyword, String agendaId, String subAgendaId) {
        mInteractor.requestAgendaPerCategorySearch(keyword, agendaId, subAgendaId, new CategoryContract.AgendaPerCategorySearchRequestCallBack() {
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
