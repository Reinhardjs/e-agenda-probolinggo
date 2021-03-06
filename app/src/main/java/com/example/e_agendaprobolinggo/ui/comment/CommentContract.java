package com.example.e_agendaprobolinggo.ui.comment;

import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.request.NewComment;
import com.example.e_agendaprobolinggo.model.response.AddCommentResponse;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;

public interface CommentContract {

    interface DetailAgendaRequestCallback {
        void onDetailAgendaRequestCompleted(DetailAgendaResponse detailAgendaResponse);

        void onDetailAgendaRequestFailure(String message);
    }

    interface AddCommentRequestCallback {
        void onAddCommentRequestCompleted(AddCommentResponse addCommentResponse);

        void onAddCommentRequestFailure(String message);
    }

    interface View {
        void populateDetailAgenda(DetailAgendaResponse detailAgendaResponse);

        void showFailureDetailAgenda(String message);

        void notifyAddCommentSuccess(AddCommentResponse addCommentResponse);

        void notifyAddCommentFailure(String message);
    }

    interface Interactor {

        void requestDetailAgenda(DetailAgenda detailAgenda, DetailAgendaRequestCallback detailAgendaRequestCallback);

        void doAddComment(NewComment newComment, AddCommentRequestCallback addCommentRequestCallback);
    }

    interface Presenter {

        void getDetailAgenda(DetailAgenda detailAgenda);

        void doAddComment(NewComment newComment);
    }
}
