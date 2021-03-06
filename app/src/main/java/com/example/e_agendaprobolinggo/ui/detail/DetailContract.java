package com.example.e_agendaprobolinggo.ui.detail;

import com.example.e_agendaprobolinggo.model.request.DeleteComment;
import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DeleteCommentResponse;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;

public interface DetailContract {

    interface DetailAgendaRequestCallback {

        void onDetailAgendaRequestCompleted(DetailAgendaResponse detailAgendaResponse);

        void onDetailAgendaRequestFailure(String message);
    }

    interface DeleteCommentRequestCallback {

        void onDeleteCommentRequestCompleted(DeleteCommentResponse deleteCommentResponse);

        void onDeleteCommentRequestFailure(String message);
    }

    interface View {

        void populateDetailAgenda(DetailAgendaResponse detailAgendaResponse);

        void showFailureDetailAgenda(String message);

        void notifyDeleteCommentSuccess(DeleteCommentResponse deleteCommentResponse);

        void notifyDeleteCommentFailure(String message);
    }

    interface Interactor {

        void requestDetailAgenda(DetailAgenda detailAgenda, DetailAgendaRequestCallback detailAgendaRequestCallback);

        void doDeleteComment(DeleteComment deleteComment, DeleteCommentRequestCallback deleteCommentRequestCallback);
    }

    interface Presenter {

        void getDetailAgenda(DetailAgenda detailAgenda);

        void doDeleteComment(DeleteComment deleteComment);
    }

}
