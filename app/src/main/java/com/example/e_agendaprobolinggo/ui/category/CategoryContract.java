package com.example.e_agendaprobolinggo.ui.category;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

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

        void requestAgendaList(String agendaId, String subAgendaId, CategoryAgendaRequestCallback categoryAgendaRequestCallback);

        void requestAgendaPerCategorySearch(String keyword, String agendaId, String subAgendaId, AgendaPerCategorySearchRequestCallBack agendaPerCategorySearchRequestCallBack);
    }

    interface Presenter {

        void getCategoryAgendaList(String agendaId, String subAgendaId);

        void getAgendaPerCategorySearch(String keyword, String agendaId, String subAgendaId);

    }
}
