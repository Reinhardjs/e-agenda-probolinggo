package com.karyadev.e_agendaprobolinggo.ui.category;

import com.karyadev.e_agendaprobolinggo.model.request.Agenda;
import com.karyadev.e_agendaprobolinggo.model.request.Search;
import com.karyadev.e_agendaprobolinggo.model.response.AgendaResponse;

public interface CategoryContract {

    interface CategoryAgendaRequestCallback {
        void onCategoryAgendaRequestCompleted(AgendaResponse agendaResponse);

        void onCategoryAgendaRequestFailure(String message);

    }

    interface AgendaPerCategorySearchRequestCallBack {
        void onAgendaPerCategorySearchRequestCompleted(AgendaResponse agendaResponse);

        void onAgendaPerCategorySearchRequestFailure(String message);
    }

    interface View {
        void populateCategoryAgenda(AgendaResponse agendaResponse);

        void showCategoryAgendaFailure(String message);

        void populateAgendaPerCategorySearch(AgendaResponse agendaResponse);

        void showAgendaPerCategorySearchFailure(String message);
    }

    interface Interactor {

        void requestAgendaList(Agenda agenda, CategoryAgendaRequestCallback categoryAgendaRequestCallback);

        void requestAgendaPerCategorySearch(Search search, AgendaPerCategorySearchRequestCallBack agendaPerCategorySearchRequestCallBack);
    }

    interface Presenter {

        void getCategoryAgendaList(Agenda agenda);

        void getAgendaPerCategorySearch(Search search);

    }
}
