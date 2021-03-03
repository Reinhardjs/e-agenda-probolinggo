package com.example.e_agendaprobolinggo.ui.calendar;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public class CalendarPresenter implements CalendarContract.Presenter {

    private CalendarContract.View mView;
    private CalendarContract.Interactor mInteractor;

    public CalendarPresenter(CalendarContract.View view) {
        mView = view;
        mInteractor = new CalendarInteractor();
    }

    @Override
    public void getAgendaCalendarList(String agendaId, String limit, String subAgendaId) {
        mInteractor.requestAgendaCalendarList(agendaId, limit, subAgendaId, new CalendarContract.AgendaCalendarRequestCallback() {
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
