package com.example.e_agendaprobolinggo.ui.calendar;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public interface CalendarContract {

    interface AgendaCalendarRequestCallback {

        void onAgendaCalendarRequestCompleted(AgendaResponse agendaResponse);

        void onAgendaCalendarRequestFailure(String message);

    }

    interface View {

        void populateAgenda(AgendaResponse agendaResponse);

        void showAgendaFailure(String message);

    }

    interface Interactor {

        void requestAgendaCalendarList(Agenda agenda, AgendaCalendarRequestCallback agendaCalendarRequestCallback);

    }

    interface Presenter {

        void getAgendaCalendarList(Agenda agenda);

    }
}
