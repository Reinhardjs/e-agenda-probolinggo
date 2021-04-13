package com.karyadev.e_agendaprobolinggo.ui.comment;

import com.karyadev.e_agendaprobolinggo.model.request.DeleteComment;
import com.karyadev.e_agendaprobolinggo.model.request.DetailAgenda;
import com.karyadev.e_agendaprobolinggo.model.request.NewComment;
import com.karyadev.e_agendaprobolinggo.model.response.AddCommentResponse;
import com.karyadev.e_agendaprobolinggo.model.response.DeleteCommentResponse;
import com.karyadev.e_agendaprobolinggo.model.response.DetailAgendaResponse;

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

    @Override
    public void doDeleteComment(DeleteComment deleteComment) {
        mInteractor.doDeleteComment(deleteComment, new CommentContract.DeleteCommentRequestCallback() {
            @Override
            public void onDeleteCommentRequestCompleted(DeleteCommentResponse deleteCommentResponse) {
                mView.notifyDeleteCommentSuccess(deleteCommentResponse);
            }

            @Override
            public void onDeleteCommentRequestFailure(String message) {
                mView.notifyDeleteCommentFailure(message);
            }
        });
    }
}
