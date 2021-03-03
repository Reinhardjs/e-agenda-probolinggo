package com.example.e_agendaprobolinggo.ui.detail;

import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public class DetailPresenter implements DetailContract.Presenter {

    private final DetailContract.View mView;
    private final DetailContract.Interactor mInteractor;

    public DetailPresenter(DetailContract.View view) {
        mView = view;
        mInteractor = new DetailInteractor();
    }
    @Override
    public void getDetailAgenda(String key) {
        mInteractor.requestDetailAgenda(key, new DetailContract.DetailAgendaRequestCallback() {
            @Override
            public void onDetailAgendaRequestCompleted(AgendaResponse agendaResponse) {
                mView.populateDetailAgenda(agendaResponse);
            }

            @Override
            public void onDetailAgendaRequestFailure(String message) {
                mView.showFailureDetailAgenda(message);
            }
        });
    }
}
