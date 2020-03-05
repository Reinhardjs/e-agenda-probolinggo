package com.example.e_agendaprobolinggo.ui.detail;

import com.example.e_agendaprobolinggo.model.response.Agenda;

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;
    private DetailContract.Interactor mInteractor;

    public DetailPresenter(DetailContract.View view) {
        mView = view;
        mInteractor = new DetailInteractor();
    }
    @Override
    public void getDetailAgenda(String key) {
        mInteractor.requestDetailAgenda(key, new DetailContract.DetailAgendaRequestCallback() {
            @Override
            public void onDetailAgendaRequestCompleted(Agenda agenda) {
                mView.populateDetailAenda(agenda);
            }

            @Override
            public void onDetailAgendaRequestFailure(String message) {
                mView.showFailureDetailAgenda(message);
            }
        });
    }
}
