package com.example.e_agendaprobolinggo.ui.category;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public interface CategoryContract {

    interface CategoryAgendaRequestCallback {
        void onCategoryAgendaRequestCompleted(AgendaResponse agendaResponse);

        void onCategoryAgendaRequestFailure(String message);
    }

    interface View {
        void populateCategoryAgenda(AgendaResponse agendaResponse);

        void showCategoryAgendaFailure(String message);
    }

    interface Interactor {

        void requestAgendaList(String agendaId, String subAgendaId, CategoryAgendaRequestCallback categoryAgendaRequestCallback);

    }

    interface Presenter {

        void getCategoryAgendaList(String agendaId, String subAgendaId);

    }
}
