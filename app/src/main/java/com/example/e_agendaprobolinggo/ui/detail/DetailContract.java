package com.example.e_agendaprobolinggo.ui.detail;

import com.example.e_agendaprobolinggo.model.response.Agenda;

public interface DetailContract {

    interface DetailAgendaRequestCallback{
        void onDetailAgendaRequestCompleted(Agenda agenda);

        void onDetailAgendaRequestFailure(String message);
    }

    interface View {
        void populateDetailAenda(Agenda agenda);

        void showFailureDetailAgenda(String message);
    }

    interface Interactor {

        void requestDetailAgenda(String key, DetailAgendaRequestCallback detailAgendaRequestCallback);

    }

    interface Presenter {

        void getDetailAgenda(String key);

    }

}
