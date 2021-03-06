package com.example.e_agendaprobolinggo.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.databinding.ActivityCommentBinding;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.request.NewComment;
import com.example.e_agendaprobolinggo.model.response.AddCommentResponse;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.example.e_agendaprobolinggo.model.response.User;
import com.example.e_agendaprobolinggo.ui.detail.DetailActivity;

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
        setupListenerOrCallback();

        binding.btnSendComment.setEnabled(false);

        code = getIntent().getStringExtra(DetailActivity.CODE);
        User user = SharedPreferenceUtils.getUser(this);
        idUser = user.getId();

        detailAgenda = new DetailAgenda(code, idUser);

        mPresenter = new CommentPresenter(this);

        commentAdapter = new CommentAdapter();

        initRequest();
    }

    private void setupToolbar() {
        Toolbar commentToolbar = binding.toolbarComment;
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

    @Override
    public void populateDetailAgenda(DetailAgendaResponse detailAgendaResponse) {
        binding.swipeRefreshLayoutComment.setRefreshing(false);
        commentAdapter.setData(detailAgendaResponse.getData().get(0).getListKomentar());
        binding.rvComment.setLayoutManager(new LinearLayoutManager(this));
        binding.rvComment.setHasFixedSize(true);
        binding.rvComment.setAdapter(commentAdapter);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent isFetchIntent = new Intent();
        isFetchIntent.putExtra(IS_FETCH, isFetch);
        setResult(RESULT_CODE, isFetchIntent);
        finish();
    }
}