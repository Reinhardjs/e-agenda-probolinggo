package com.example.e_agendaprobolinggo.ui.home;

import com.example.e_agendaprobolinggo.model.body.AgendaType;
import com.example.e_agendaprobolinggo.model.response.Agenda;

import java.util.ArrayList;

public interface HomeContract {

    interface AgendaRequestCallback {
        void onAgendaRequestCompleted(Agenda agenda);

        void onAgendaRequestFailure(String message);
    }

    interface AgendaTypeRequestCallback {
        void onAgendaTypeRequestCompleted(ArrayList<AgendaType> agendaTypes);

        void onAgendaTypeRequestFailure(String message);
    }

    interface View {
        void populateAgenda(Agenda agenda);

        void showAgendaFailure(String message);

        void populateAgendaType(ArrayList<AgendaType> agendaTypes);

        void showAgendaTypeFailure(String message);
    }

    interface Interactor {

        void requestAgendaList(AgendaRequestCallback agendaRequestCallback);

        void requestAgendaTypeList(AgendaTypeRequestCallback agendaTypeRequestCallback);

    }

    interface Presenter {

        void requestAgendaList();

        void requestAgendaTypeList();

    }

}
