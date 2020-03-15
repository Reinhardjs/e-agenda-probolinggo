package com.example.e_agendaprobolinggo.ui.all_agenda;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public interface AllAgendaContract {

    interface AllAgendaRequestCallback {
        void onAllAgendaRequestCompleted(AgendaResponse agendaResponse);

        void onAllAgendaRequestFailure(String message);
    }

    interface View {
        void populateAllAgenda(AgendaResponse agendaResponse);

        void showAllAgendaFailure(String message);
    }

    interface Interactor {

        void requestAllAgendaList(String agendaId, String limit, String subAgendaId, AllAgendaRequestCallback allAgendaRequestCallback);

    }

    interface Presenter {
        void getAllAgendaList(String agendaId, String limit, String subAgendaId);
    }
}
