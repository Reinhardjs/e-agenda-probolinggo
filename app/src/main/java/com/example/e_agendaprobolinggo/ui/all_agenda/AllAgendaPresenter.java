package com.example.e_agendaprobolinggo.ui.all_agenda;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public class AllAgendaPresenter implements AllAgendaContract.Presenter{

    private AllAgendaContract.View mView;
    private AllAgendaContract.Interactor mInteractor;

    public AllAgendaPresenter(AllAgendaContract.View view){
        mView = view;
        mInteractor = new AllAgendaInteractor();
    }

    @Override
    public void getAllAgendaList(String agendaId, String limit, String subAgendaId) {
        mInteractor.requestAllAgendaList(agendaId, limit, subAgendaId, new AllAgendaContract.AllAgendaRequestCallback() {
            @Override
            public void onAllAgendaRequestCompleted(AgendaResponse agendaResponse) {
                mView.populateAllAgenda(agendaResponse);
            }

            @Override
            public void onAllAgendaRequestFailure(String message) {
                mView.showAllAgendaFailure(message);
            }
        });
    }

    @Override
    public void requestAgendaSearch(String keyword) {
        mInteractor.requestAgendaSearch(keyword, new AllAgendaContract.SearchRequestCallback() {
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
