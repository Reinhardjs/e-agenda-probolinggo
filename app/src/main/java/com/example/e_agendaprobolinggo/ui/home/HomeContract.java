package com.example.e_agendaprobolinggo.ui.home;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.KategoriResponse;

public interface HomeContract {

    interface AgendaRequestCallback {
        void onAgendaRequestCompleted(AgendaResponse agendaResponse);

        void onAgendaRequestFailure(String message);
    }

    interface AgendaTypeRequestCallback {
        void onAgendaTypeRequestCompleted(KategoriResponse agendaTypes);

        void onAgendaTypeRequestFailure(String message);
    }

    interface SearchRequestCallback {
        void onSearchRequestCompleted(AgendaResponse agendaResponse);

        void onSearchRequestFailure(String message);
    }

    interface View {
        void populateAgenda(AgendaResponse agendaResponse);

        void showAgendaFailure(String message);

        void populateAgendaType(KategoriResponse agendaTypes);

        void showAgendaTypeFailure(String message);

        void populateAgendaSearch(AgendaResponse agendaResponse);

        void showAgendaSearchFailure(String message);
    }

    interface Interactor {

        void requestAgendaList(AgendaRequestCallback agendaRequestCallback);

        void requestAgendaTypeList(AgendaTypeRequestCallback agendaTypeRequestCallback);

        void requestAgendaSearch(String keyword, SearchRequestCallback searchRequestCallback);

    }

    interface Presenter {

        void requestAgendaList();

        void requestAgendaTypeList();

        void requestAgendaSearch(String keyword);

    }

}
