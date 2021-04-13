package com.karyadev.e_agendaprobolinggo.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.karyadev.e_agendaprobolinggo.R;
import com.karyadev.e_agendaprobolinggo.databinding.ActivityCommentBinding;
import com.karyadev.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.karyadev.e_agendaprobolinggo.model.request.DeleteComment;
import com.karyadev.e_agendaprobolinggo.model.request.DetailAgenda;
import com.karyadev.e_agendaprobolinggo.model.request.NewComment;
import com.karyadev.e_agendaprobolinggo.model.response.AddCommentResponse;
import com.karyadev.e_agendaprobolinggo.model.response.DataDetailAgenda;
import com.karyadev.e_agendaprobolinggo.model.response.DeleteCommentResponse;
import com.karyadev.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.karyadev.e_agendaprobolinggo.model.response.ListKomentarAgenda;
import com.karyadev.e_agendaprobolinggo.model.response.User;
import com.karyadev.e_agendaprobolinggo.ui.detail.DetailActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CommentActivity extends AppCompatActivity implements CommentContract.View {

    public static final String IS_FETCH = "is_fetch";
    public static final int RESULT_CODE = 110;

    private ActivityCommentBinding binding;
    private CommentContract.Presenter mPresenter;
    private DetailAgenda detailAgenda;
    private CommentAdapter commentAdapter;

    private String code;
    private String idUser;

    private boolean isFetch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();

        binding.btnSendComment.setEnabled(false);

        code = getIntent().getStringExtra(DetailActivity.CODE);
        User user = SharedPreferenceUtils.getUser(this);
        idUser = user.getId();

        detailAgenda = new DetailAgenda(code, idUser);

        mPresenter = new CommentPresenter(this);

        commentAdapter = new CommentAdapter();

        setupListenerOrCallback();
        initRequest();
        iniRecyclerView();
    }

    private void iniRecyclerView() {
        binding.rvComment.setLayoutManager(new LinearLayoutManager(this));
        binding.rvComment.setHasFixedSize(true);
        binding.rvComment.setAdapter(commentAdapter);
    }

    private void setupToolbar() {
        MaterialToolbar commentToolbar = binding.toolbarComment;
        setSupportActionBar(commentToolbar);
        TextView toolbarTitle = commentToolbar.findViewById(R.id.toolbar_title_comment);
        toolbarTitle.setText(getResources().getString(R.string.comment_text));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupListenerOrCallback() {
        binding.swipeRefreshLayoutComment.setOnRefreshListener(this::initRequest);

        binding.etAddComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.btnSendComment.setEnabled(charSequence.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btnSendComment.setOnClickListener(view -> sendComment());

        commentAdapter.setOnItemClickCallback(comment -> new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle(getResources().getString(R.string.delete_comment_title))
                .setMessage(getResources().getString(R.string.delete_comment_message))
                .setNegativeButton(getResources().getString(R.string.no_text), (dialogInterface, i) -> {
                })
                .setPositiveButton(getResources().getString(R.string.yes_text), (dialogInterface, i) -> deleteComment(comment)).show());
    }

    private void initRequest() {
        binding.swipeRefreshLayoutComment.setRefreshing(true);
        mPresenter.getDetailAgenda(detailAgenda);
    }

    private void sendComment() {
        binding.swipeRefreshLayoutComment.setRefreshing(true);
        String comment = binding.etAddComment.getText().toString();
        NewComment newComment = new NewComment(code, idUser, comment);
        binding.etAddComment.setText(null);

        mPresenter.doAddComment(newComment);
    }

    private void deleteComment(ListKomentarAgenda comment) {
        binding.swipeRefreshLayoutComment.setRefreshing(true);
        DeleteComment deleteComment = new DeleteComment(comment.getId(), idUser);

        mPresenter.doDeleteComment(deleteComment);
    }

    @Override
    public void populateDetailAgenda(DetailAgendaResponse detailAgendaResponse) {
        binding.swipeRefreshLayoutComment.setRefreshing(false);

        DataDetailAgenda dataDetailAgenda = detailAgendaResponse.getData().get(0);

        if (dataDetailAgenda.getTampilkanKomentar() == 1) {
            binding.rvComment.setVisibility(View.VISIBLE);
            commentAdapter.setData(dataDetailAgenda.getListKomentar());
            binding.rvComment.scrollToPosition(dataDetailAgenda.getListKomentar().size() - 1);
            binding.rvComment.addOnLayoutChangeListener((view, i, i1, i2, i3, i4, i5, i6, i7) -> binding.rvComment.scrollToPosition(dataDetailAgenda.getListKomentar().size() - 1));

        } else {
            binding.rvComment.setVisibility(View.VISIBLE);
        }

        if (detailAgendaResponse.getData().get(0).getBtnTambahKomentar() == 1) {
            binding.etAddComment.setVisibility(View.VISIBLE);
            binding.btnSendComment.setVisibility(View.VISIBLE);
        } else {
            binding.etAddComment.setVisibility(View.GONE);
            binding.btnSendComment.setVisibility(View.GONE);
        }
    }

    @Override
    public void showFailureDetailAgenda(String message) {
        binding.swipeRefreshLayoutComment.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyAddCommentSuccess(AddCommentResponse addCommentResponse) {
        isFetch = true;
        Toast.makeText(this, addCommentResponse.getMessage(), Toast.LENGTH_SHORT).show();
        initRequest();
        binding.btnSendComment.setEnabled(false);
    }

    @Override
    public void notifyAddCommentFailure(String message) {
        binding.swipeRefreshLayoutComment.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDeleteCommentSuccess(DeleteCommentResponse deleteCommentResponse) {
        isFetch = true;
        Toast.makeText(this, deleteCommentResponse.getMessage(), Toast.LENGTH_SHORT).show();
        initRequest();
    }

    @Override
    public void notifyDeleteCommentFailure(String message) {
        binding.swipeRefreshLayoutComment.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent fetchIntent = new Intent();
        fetchIntent.putExtra(IS_FETCH, isFetch);
        setResult(RESULT_CODE, fetchIntent);
        finish();
    }
}