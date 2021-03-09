package com.example.e_agendaprobolinggo.ui.detail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.request.DeleteComment;
import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DataDetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DeleteCommentResponse;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.example.e_agendaprobolinggo.model.response.ListKomentarAgenda;
import com.example.e_agendaprobolinggo.model.response.User;
import com.example.e_agendaprobolinggo.ui.comment.CommentActivity;
import com.example.e_agendaprobolinggo.ui.comment.CommentAdapter;
import com.example.e_agendaprobolinggo.utils.DownloadManager;
import com.example.e_agendaprobolinggo.utils.Permission;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public static final String CODE = "code";

    private DetailContract.Presenter mPresenter;
    private TextView tvDetailAgenda, labelComment, tvShowAllComment;
    private String urlLetter, urlSambutan, code, idUser;
    private Button btnLetter, btnSambutan;
    private View lineComment;
    private RecyclerView rvComment;
    private ProgressDialog mProgressDialog;
    private ConstraintLayout containerDetail;
    private DetailAgenda detailAgenda;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CommentAdapter commentAdapter;
    private Permission permission;
    private final String path = Environment.getExternalStorageDirectory() + "/Download/E-Agenda";

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == CommentActivity.RESULT_CODE) {
            if (result.getData() != null) {
                boolean isFetch = result.getData().getBooleanExtra(CommentActivity.IS_FETCH, false);

                if (isFetch) {
                    initRequest();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupToolbar();
        initView();

        code = getIntent().getStringExtra(CODE);

        containerDetail.setVisibility(View.GONE);
        commentAdapter = new CommentAdapter();

        User user = SharedPreferenceUtils.getUser(this);
        idUser = user.getId();

        detailAgenda = new DetailAgenda(code, idUser);

        mPresenter = new DetailPresenter(this);

        permission = new Permission();

        setupListenerOrCallback();
        initRequest();
    }

    private void setupToolbar() {
        MaterialToolbar toolbarDetail = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbarDetail);
        TextView toolbarTitle = toolbarDetail.findViewById(R.id.toolbar_title_detail);
        toolbarTitle.setText(R.string.detail_toolbar_title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        containerDetail = findViewById(R.id.container_detail);

        tvDetailAgenda = findViewById(R.id.tv_detail_agenda);
        btnLetter = findViewById(R.id.btn_download_letter);
        btnSambutan = findViewById(R.id.btn_download_sambutan);
        lineComment = findViewById(R.id.line_comment);
        labelComment = findViewById(R.id.label_comment);
        tvShowAllComment = findViewById(R.id.tv_show_all_comment);
        rvComment = findViewById(R.id.rv_comment);
    }

    private void setupListenerOrCallback() {
        btnLetter.setOnClickListener(v -> {
            if (!permission.checkStoragePermission(this)) {
                permission.getStoragePermission(this);
            } else {
                downloadFileAgenda(urlLetter);
            }

        });

        btnSambutan.setOnClickListener(v -> {
            if (!permission.checkStoragePermission(this)) {
                permission.getStoragePermission(this);
            } else {
                downloadFileAgenda(urlSambutan);
            }
        });

        tvShowAllComment.setOnClickListener(v -> {
            Intent intent = new Intent(this, CommentActivity.class);
            intent.putExtra(CODE, code);
            activityResultLauncher.launch(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getDetailAgenda(detailAgenda);
            containerDetail.setVisibility(View.GONE);
        });

        commentAdapter.setOnItemClickCallback(comment -> new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle(getResources().getString(R.string.delete_comment_title))
                .setMessage(getResources().getString(R.string.delete_comment_message))
                .setNegativeButton(getResources().getString(R.string.no_text), (dialogInterface, i) -> {
                })
                .setPositiveButton(getResources().getString(R.string.yes_text), (dialogInterface, i) -> deleteComment(comment)).show());
    }

    private void initRequest() {
        swipeRefreshLayout.setRefreshing(true);
        mPresenter.getDetailAgenda(detailAgenda);
    }

    private void downloadFileAgenda(String url) {
        mProgressDialog = new ProgressDialog(DetailActivity.this);
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.show();
        DownloadManager downloadManager = new DownloadManager();
        downloadManager.download(url, path, new DownloadManager.Callback() {
            @Override
            public void onDownloadStarted(long totalLength) {

            }

            @Override
            public void onDownloadProgress(long totalLength, long downloadedLength) {
                mProgressDialog.setProgress((int) (downloadedLength * 100 / totalLength));
            }

            @Override
            public void onDownloadComplete(File file) {
                mProgressDialog.dismiss();
                Toast.makeText(DetailActivity.this, "Download berhasil\nLokasi file di Penyimpanan/Download/E-Agenda", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDownloadError(Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(DetailActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteComment(ListKomentarAgenda comment) {
        swipeRefreshLayout.setRefreshing(true);
        DeleteComment deleteComment = new DeleteComment(comment.getId(), idUser);
        mPresenter.doDeleteComment(deleteComment);
    }

    @Override
    public void populateDetailAgenda(DetailAgendaResponse detailAgendaResponse) {
        swipeRefreshLayout.setRefreshing(false);
        containerDetail.setVisibility(View.VISIBLE);

        DataDetailAgenda dataDetailAgenda = detailAgendaResponse.getData().get(0);
        tvDetailAgenda.setText(Html.fromHtml(dataDetailAgenda.getDetail()));

        urlLetter = dataDetailAgenda.getSurat();
        urlSambutan = dataDetailAgenda.getSambutan();

        if (dataDetailAgenda.getBtnDownloadSurat() == 1) {
            btnLetter.setVisibility(View.VISIBLE);
            btnLetter.setEnabled(!urlLetter.isEmpty() && urlLetter.charAt(urlLetter.length() - 1) != '-');
        } else {
            btnLetter.setVisibility(View.GONE);
        }

        if (dataDetailAgenda.getBtnDownloadSambutan() == 1) {
            btnSambutan.setVisibility(View.VISIBLE);
            btnSambutan.setEnabled(!urlSambutan.isEmpty() && urlSambutan.charAt(urlSambutan.length() - 1) != '-');
        } else {
            btnSambutan.setVisibility(View.GONE);
        }

        if (dataDetailAgenda.getTampilkanKomentar() == 1) {
            lineComment.setVisibility(View.VISIBLE);
            labelComment.setVisibility(View.VISIBLE);
            rvComment.setVisibility(View.VISIBLE);

            List<ListKomentarAgenda> listComment = dataDetailAgenda.getListKomentar();

            if (listComment.size() > 3) {
                tvShowAllComment.setVisibility(View.VISIBLE);
                tvShowAllComment.setText(getResources().getString(R.string.show_other_comment, listComment.size() - 3));
                commentAdapter.setData(listComment.subList(0, 3));
            } else {
                if (dataDetailAgenda.getBtnTambahKomentar() == 1) {
                    tvShowAllComment.setVisibility(View.VISIBLE);
                    tvShowAllComment.setText(getResources().getString(R.string.add_comment_text));
                } else {
                    tvShowAllComment.setVisibility(View.GONE);
                }
                commentAdapter.setData(listComment);
            }
            rvComment.setLayoutManager(new LinearLayoutManager(this));
            rvComment.setHasFixedSize(true);
            rvComment.setAdapter(commentAdapter);

        } else {
            rvComment.setVisibility(View.GONE);
            if (dataDetailAgenda.getBtnTambahKomentar() == 1) {
                lineComment.setVisibility(View.VISIBLE);
                labelComment.setVisibility(View.VISIBLE);
                tvShowAllComment.setVisibility(View.VISIBLE);
                tvShowAllComment.setText(getResources().getString(R.string.add_comment_text));
            } else {
                lineComment.setVisibility(View.GONE);
                labelComment.setVisibility(View.GONE);
                tvShowAllComment.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showFailureDetailAgenda(String message) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDeleteCommentSuccess(DeleteCommentResponse deleteCommentResponse) {
        Toast.makeText(this, deleteCommentResponse.getMessage(), Toast.LENGTH_SHORT).show();
        initRequest();
    }

    @Override
    public void notifyDeleteCommentFailure(String message) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(menuItem);
    }

}
