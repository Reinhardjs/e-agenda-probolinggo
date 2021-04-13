package com.karyadev.e_agendaprobolinggo.ui.home;

import com.karyadev.e_agendaprobolinggo.model.request.Agenda;
import com.karyadev.e_agendaprobolinggo.model.request.Search;
import com.karyadev.e_agendaprobolinggo.model.response.AgendaResponse;
import com.karyadev.e_agendaprobolinggo.model.response.KategoriResponse;

public interface HomeContract {

    interface AgendaRequestCallback {

        void onAgendaRequestCompleted(AgendaResponse agendaResponse);

        void onAgendaRequestFailure(String message);

    }

    interface AgendaCategoryRequestCallback {

        void onAgendaCategoryRequestCompleted(KategoriResponse agendaCategories);

        void onAgendaCategoryRequestFailure(String message);

    }

    interface SearchRequestCallback {

        void onSearchRequestCompleted(AgendaResponse agendaResponse);

        void onSearchRequestFailure(String message);

    }

    interface View {

        void populateAgenda(AgendaResponse agendaResponse);

        void showAgendaFailure(String message);

        void populateAgendaCategory(KategoriResponse agendaCategories);

        void showAgendaCategoryFailure(String message);

        void populateAgendaSearch(AgendaResponse agendaResponse);

        void showAgendaSearchFailure(String message);

    }

    interface Interactor {

        void requestAgendaList(Agenda agenda, AgendaRequestCallback agendaRequestCallback);

        void requestAgendaCategoryList(AgendaCategoryRequestCallback agendaCategoryRequestCallback);

        void requestAgendaSearch(Search search, SearchRequestCallback searchRequestCallback);

    }

    interface Presenter {

        void requestAgendaList(Agenda agenda);

        void requestAgendaCategoryList();

        void requestAgendaSearch(Search search);

    }

}
