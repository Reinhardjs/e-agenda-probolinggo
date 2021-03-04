package com.example.e_agendaprobolinggo.ui.detail;

import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;

public class DetailPresenter implements DetailContract.Presenter {

    private final DetailContract.View mView;
    private final DetailContract.Interactor mInteractor;

    public DetailPresenter(DetailContract.View view) {
        mView = view;
        mInteractor = new DetailInteractor();
    }

    @Override
    public void getDetailAgenda(DetailAgenda detailAgenda) {
        mInteractor.requestDetailAgenda(detailAgenda, new DetailContract.DetailAgendaRequestCallback() {
            @Override
            public void onDetailAgendaRequestCompleted(DetailAgendaResponse detailAgendaResponse) {
                mView.populateDetailAgenda(detailAgendaResponse);
            }

            @Override
            public void onDetailAgendaRequestFailure(String message) {
                mView.showFailureDetailAgenda(message);
            }
        });
    }
}
