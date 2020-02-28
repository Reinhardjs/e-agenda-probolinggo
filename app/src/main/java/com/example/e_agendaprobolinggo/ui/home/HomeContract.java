package com.example.e_agendaprobolinggo.ui.home;

import com.example.e_agendaprobolinggo.model.response.Agenda;

import java.util.ArrayList;

public interface HomeContract {

    interface AgendaRequestCallback {
        void onAgendaRequestCompleted(Agenda agenda);

        void onAgendaRequestFailure(String message);
    }

    interface CategoryRequestCallback {
        void onCategoryRequestCompleted(ArrayList<String> categories);

        void onCategoryRequestFailure(String message);
    }

    interface View {
        void populateAgenda(Agenda agenda);

        void showAgendaFailure(String message);

        void populateCategory(ArrayList<String> categories);

        void showCategoryFailure(String message);
    }

    interface Interactor {

        void requestAgendaList(AgendaRequestCallback agendaRequestCallback);

        void requestCategoryList(CategoryRequestCallback categoryRequestCallback);

    }

    interface Presenter {

        void getAgendaList();

        void getCategoryList();

    }

}
