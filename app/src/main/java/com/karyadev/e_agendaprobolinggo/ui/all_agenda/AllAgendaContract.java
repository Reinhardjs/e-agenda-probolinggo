package com.karyadev.e_agendaprobolinggo.ui.all_agenda;

import com.karyadev.e_agendaprobolinggo.model.request.Agenda;
import com.karyadev.e_agendaprobolinggo.model.request.Search;
import com.karyadev.e_agendaprobolinggo.model.response.AgendaResponse;

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

        void requestAllAgendaList(Agenda agenda, AllAgendaRequestCallback allAgendaRequestCallback);

        void requestAgendaSearch(Search search, SearchRequestCallback searchRequestCallback);

    }

    interface Presenter {

        void getAllAgendaList(Agenda agenda);

        void requestAgendaSearch(Search search);

    }
}
