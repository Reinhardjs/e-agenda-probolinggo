package com.example.e_agendaprobolinggo.ui.detail;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public interface DetailContract {

    interface DetailAgendaRequestCallback{
        void onDetailAgendaRequestCompleted(AgendaResponse agendaResponse);

        void onDetailAgendaRequestFailure(String message);
    }

    interface View {
        void populateDetailAenda(AgendaResponse agendaResponse);

        void showFailureDetailAgenda(String message);
    }

    interface Interactor {

        void requestDetailAgenda(String key, DetailAgendaRequestCallback detailAgendaRequestCallback);

    }

    interface Presenter {

        void getDetailAgenda(String key);

    }

}
