package com.example.e_agendaprobolinggo.ui.all_agenda;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public interface AllAgendaContract {

    interface AllAgendaRequestCallback {

        void onAllAgendaRequestCompleted(AgendaResponse agendaResponse);

        void onAllAgendaRequestFailure(String message);

    }

    interface SearchRequestCallback {

        void onSearchRequestCompleted(AgendaResponse agendaResponse);

        void onSearchRequestFailure(String message);

    }

    interface View {

        void populateAllAgenda(AgendaResponse agendaResponse);

        void showAllAgendaFailure(String message);

        void populateAgendaSearch(AgendaResponse agendaResponse);

        void showAgendaSearchFailure(String message);

    }

    interface Interactor {

        void requestAllAgendaList(String agendaId, String limit, String subAgendaId, AllAgendaRequestCallback allAgendaRequestCallback);

        void requestAgendaSearch(String keyword, SearchRequestCallback searchRequestCallback);

    }

    interface Presenter {

        void getAllAgendaList(String agendaId, String limit, String subAgendaId);

        void requestAgendaSearch(String keyword);

    }
}
