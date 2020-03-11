package com.example.e_agendaprobolinggo.ui.category;

import com.example.e_agendaprobolinggo.model.response.Agenda;

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View mView;
    private CategoryContract.Interactor mInteractor;

    public CategoryPresenter(CategoryContract.View view){
        mView = view;
        mInteractor = new CategoryInteractor();
    }
    @Override
    public void getCategoryAgendaList(String category) {
        mInteractor.requestAgendaList(category, new CategoryContract.CategoryAgendaRequestCallback() {
            @Override
            public void onCategoryAgendaRequestCompleted(Agenda agenda) {
                mView.populateCategoryAgenda(agenda);
            }

            @Override
            public void onCategoryAgendaRequestFailure(String message) {
                mView.showCategoryAgendaFailure(message);
            }
        });
    }
}
