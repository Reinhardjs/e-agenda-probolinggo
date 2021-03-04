package com.example.e_agendaprobolinggo.ui.all_agenda;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.request.Search;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public class AllAgendaPresenter implements AllAgendaContract.Presenter {

    private final AllAgendaContract.View mView;
    private final AllAgendaContract.Interactor mInteractor;

    public AllAgendaPresenter(AllAgendaContract.View view) {
        mView = view;
        mInteractor = new AllAgendaInteractor();
    }

    @Override
    public void getAllAgendaList(Agenda agenda) {
        mInteractor.requestAllAgendaList(agenda, new AllAgendaContract.AllAgendaRequestCallback() {
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
    public void requestAgendaSearch(Search search) {
        mInteractor.requestAgendaSearch(search, new AllAgendaContract.SearchRequestCallback() {
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
