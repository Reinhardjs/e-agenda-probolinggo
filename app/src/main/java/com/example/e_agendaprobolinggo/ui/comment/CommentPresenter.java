package com.example.e_agendaprobolinggo.ui.comment;

import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.request.NewComment;
import com.example.e_agendaprobolinggo.model.response.AddCommentResponse;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;

public class CommentPresenter implements CommentContract.Presenter {

    private final CommentContract.View mView;
    private final CommentContract.Interactor mInteractor;

    public CommentPresenter(CommentContract.View view) {
        mView = view;
        mInteractor = new CommentInteractor();
    }

    @Override
    public void getDetailAgenda(DetailAgenda detailAgenda) {
        mInteractor.requestDetailAgenda(detailAgenda, new CommentContract.DetailAgendaRequestCallback() {
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

    @Override
    public void doAddComment(NewComment newComment) {
        mInteractor.doAddComment(newComment, new CommentContract.AddCommentRequestCallback() {
            @Override
            public void onAddCommentRequestCompleted(AddCommentResponse addCommentResponse) {
                mView.notifyAddCommentSuccess(addCommentResponse);
            }

            @Override
            public void onAddCommentRequestFailure(String message) {
                mView.notifyAddCommentFailure(message);
            }
        });
    }
}
