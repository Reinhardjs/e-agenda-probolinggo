package com.karyadev.e_agendaprobolinggo.ui.comment;

import com.karyadev.e_agendaprobolinggo.model.request.DeleteComment;
import com.karyadev.e_agendaprobolinggo.model.request.DetailAgenda;
import com.karyadev.e_agendaprobolinggo.model.request.NewComment;
import com.karyadev.e_agendaprobolinggo.model.response.AddCommentResponse;
import com.karyadev.e_agendaprobolinggo.model.response.DeleteCommentResponse;
import com.karyadev.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.karyadev.e_agendaprobolinggo.network.NetworkApi;
import com.karyadev.e_agendaprobolinggo.network.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class CommentInteractor implements CommentContract.Interactor {

    private final NetworkApi networkApi = UtilsApi.getApiService();
    private DetailAgendaResponse detailAgendaResponse = null;
    private AddCommentResponse addCommentResponse = null;
    private DeleteCommentResponse deleteCommentResponse = null;

    @Override
    public void requestDetailAgenda(DetailAgenda detailAgenda, CommentContract.DetailAgendaRequestCallback detailAgendaRequestCallback) {

        networkApi.getDetailAgenda(detailAgenda).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DetailAgendaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DetailAgendaResponse response) {
                        detailAgendaResponse = response;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = Objects.requireNonNull(((HttpException) e).response()).errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(Objects.requireNonNull(errorResponse).string());
                                detailAgendaRequestCallback.onDetailAgendaRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (detailAgendaResponse != null) {
                            if (detailAgendaResponse.isStatus()) {
                                detailAgendaRequestCallback.onDetailAgendaRequestCompleted(detailAgendaResponse);
                            } else {
                                detailAgendaRequestCallback.onDetailAgendaRequestFailure(detailAgendaResponse.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void doAddComment(NewComment newComment, CommentContract.AddCommentRequestCallback addCommentRequestCallback) {
        networkApi.addComment(newComment).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddCommentResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AddCommentResponse response) {
                        addCommentResponse = response;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                addCommentRequestCallback.onAddCommentRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (addCommentResponse != null) {
                            if (addCommentResponse.isStatus()) {
                                addCommentRequestCallback.onAddCommentRequestCompleted(addCommentResponse);
                            } else {
                                addCommentRequestCallback.onAddCommentRequestFailure(addCommentResponse.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void doDeleteComment(DeleteComment deleteComment, CommentContract.DeleteCommentRequestCallback deleteCommentRequestCallback) {
        networkApi.deleteComment(deleteComment).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteCommentResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DeleteCommentResponse response) {
                        deleteCommentResponse = response;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody errorResponse = ((HttpException) e).response().errorBody();

                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse.string());
                                deleteCommentRequestCallback.onDeleteCommentRequestFailure(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (deleteCommentResponse != null) {
                            if (deleteCommentResponse.isStatus()) {
                                deleteCommentRequestCallback.onDeleteCommentRequestCompleted(deleteCommentResponse);
                            } else {
                                deleteCommentRequestCallback.onDeleteCommentRequestFailure(deleteCommentResponse.getMessage());
                            }
                        }
                    }
                });
    }
}
