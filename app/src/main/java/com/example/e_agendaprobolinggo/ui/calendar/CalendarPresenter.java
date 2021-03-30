package com.example.e_agendaprobolinggo.ui.calendar;

import com.example.e_agendaprobolinggo.model.request.Agenda;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public class CalendarPresenter implements CalendarContract.Presenter {

    private final CalendarContract.View mView;
    private final CalendarContract.Interactor mInteractor;

    public CalendarPresenter(CalendarContract.View view) {
        mView = view;
        mInteractor = new CalendarInteractor();
    }

    @Override
    public void getAgendaCalendarList(Agenda agenda) {
        mInteractor.requestAgendaCalendarList(agenda, new CalendarContract.AgendaCalendarRequestCallback() {
            @Override
            public void onAgendaCalendarRequestCompleted(AgendaResponse agendaResponse) {
                mView.populateAgenda(agendaResponse);
            }

            @Override
            public void onAgendaCalendarRequestFailure(String message) {
                mView.showAgendaFailure(message);
            }
        });
    }
}
