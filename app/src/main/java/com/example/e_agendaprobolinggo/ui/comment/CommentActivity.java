package com.example.e_agendaprobolinggo.ui.comment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.databinding.ActivityCommentBinding;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.example.e_agendaprobolinggo.model.response.User;
import com.example.e_agendaprobolinggo.ui.detail.DetailActivity;
import com.example.e_agendaprobolinggo.ui.detail.DetailContract;
import com.example.e_agendaprobolinggo.ui.detail.DetailPresenter;

public class CommentActivity extends AppCompatActivity implements DetailContract.View {

    private ActivityCommentBinding binding;
    private DetailContract.Presenter mPresenter;
    private DetailAgenda detailAgenda;
    private CommentAdapter commentAdapter;
    private SwipeRefreshLayout swipeRefreshLayoutComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();
        setupListenerOrCallback();

        binding.swipeRefreshLayoutComment.setRefreshing(true);

        String code = getIntent().getStringExtra(DetailActivity.CODE);
        User user = SharedPreferenceUtils.getUser(this);
        int idUser = Integer.parseInt(user.getId());

        detailAgenda = new DetailAgenda(code, idUser);

        mPresenter = new DetailPresenter(this);

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
    }

    private void initRequest() {
        mPresenter.getDetailAgenda(detailAgenda);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}