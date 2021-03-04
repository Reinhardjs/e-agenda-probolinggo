package com.example.e_agendaprobolinggo.ui.detail;

import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;

public interface DetailContract {

    interface DetailAgendaRequestCallback {
        void onDetailAgendaRequestCompleted(DetailAgendaResponse detailAgendaResponse);

        void onDetailAgendaRequestFailure(String message);
    }

    interface View {
        void populateDetailAgenda(DetailAgendaResponse detailAgendaResponse);

        void showFailureDetailAgenda(String message);
    }

    interface Interactor {

        void requestDetailAgenda(DetailAgenda detailAgenda, DetailAgendaRequestCallback detailAgendaRequestCallback);

    }

    interface Presenter {

        void getDetailAgenda(DetailAgenda detailAgenda);

    }

}
