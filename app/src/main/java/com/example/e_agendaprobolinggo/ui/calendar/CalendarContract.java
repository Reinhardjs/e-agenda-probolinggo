package com.example.e_agendaprobolinggo.ui.calendar;

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

        void requestAgendaCalendarList(String agendaId, String limit, String subAgendaId, AgendaCalendarRequestCallback agendaCalendarRequestCallback);

    }

    interface Presenter {

        void getAgendaCalendarList(String agendaId, String limit, String subAgendaId);

    }
}
