package com.example.e_agendaprobolinggo.ui.category;

import com.example.e_agendaprobolinggo.model.response.Agenda;

public interface CategoryContract {

    interface CategoryAgendaRequestCallback {
        void onCategoryAgendaRequestCompleted(Agenda agenda);

        void onCategoryAgendaRequestFailure(String message);
    }

    interface View {
        void populateCategoryAgenda(Agenda agenda);

        void showCategoryAgendaFailure(String message);
    }

    interface Interactor {

        void requestAgendaList(String category, CategoryAgendaRequestCallback categoryAgendaRequestCallback);

    }

    interface Presenter {

        void getCategoryAgendaList(String category);

    }
}
