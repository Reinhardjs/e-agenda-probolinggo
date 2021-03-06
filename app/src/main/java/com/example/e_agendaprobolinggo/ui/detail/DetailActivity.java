package com.example.e_agendaprobolinggo.ui.detail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DataDetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.example.e_agendaprobolinggo.model.response.ListKomentarAgenda;
import com.example.e_agendaprobolinggo.model.response.User;
import com.example.e_agendaprobolinggo.ui.comment.CommentActivity;
import com.example.e_agendaprobolinggo.ui.comment.CommentAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public static final String CODE = "code";

    private DetailContract.Presenter mPresenter;
    private TextView tvDetailAgenda, labelComment, tvShowAllComment;
    private String urlLetter, urlSambutan;
    private String fileName;
    private Button btnLetter, btnSambutan;
    private View lineComment;
    private RecyclerView rvComment;
    private ProgressDialog mProgressDialog;
    private DownloadTask downloadTask;
    private ConstraintLayout containerDetail;
    private DetailAgenda detailAgenda;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CommentAdapter commentAdapter;
    private String code;

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

    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        } catch (MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupToolbar();
        initView();
        setupListenerOrCallback();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        swipeRefreshLayout.setRefreshing(true);
        containerDetail.setVisibility(View.GONE);

        commentAdapter = new CommentAdapter();

        code = getIntent().getStringExtra(CODE);
        User user = SharedPreferenceUtils.getUser(this);

        detailAgenda = new DetailAgenda(code, user.getId());

        mPresenter = new DetailPresenter(this);

        initRequest();
    }

    private void initRequest() {
        mPresenter.getDetailAgenda(detailAgenda);
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

        mProgressDialog = new ProgressDialog(DetailActivity.this);
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
    }

    private void setupToolbar() {
        Toolbar toolbarDetail = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbarDetail);
        TextView toolbarTitle = toolbarDetail.findViewById(R.id.toolbar_title_detail);
        toolbarTitle.setText(R.string.detail_toolbar_title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupListenerOrCallback() {
        btnLetter.setOnClickListener(v -> {

            // "https://dev.karyastudio.com/e-agenda/webfile/no_image.png"
            fileName = getFileNameFromURL(urlLetter);

            if (checkDirectoryAndFileExists(fileName)) {
                downloadTask = new DownloadTask(DetailActivity.this);
                downloadTask.execute(urlLetter);
            }

        });
        btnLetter.setEnabled(false);

        btnSambutan.setOnClickListener(v -> {
            // "https://dev.karyastudio.com/e-agenda/webfile/6950c16c9bcc6995f376b297f163175926421.png"
            fileName = getFileNameFromURL(urlSambutan);

            if (checkDirectoryAndFileExists(fileName)) {
                downloadTask = new DownloadTask(DetailActivity.this);
                downloadTask.execute(urlSambutan);
            }
        });
        btnSambutan.setEnabled(false);

        tvShowAllComment.setOnClickListener(v -> {
            Intent intent = new Intent(this, CommentActivity.class);
            intent.putExtra(CODE, code);
            activityResultLauncher.launch(intent);
        });

        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", (dialog, which) -> {
            downloadTask.cancel(true);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getDetailAgenda(detailAgenda);
            containerDetail.setVisibility(View.GONE);
        });
    }

    @Override
    public void populateDetailAgenda(DetailAgendaResponse detailAgendaResponse) {
        swipeRefreshLayout.setRefreshing(false);
        containerDetail.setVisibility(View.VISIBLE);

        DataDetailAgenda dataDetailAgenda = detailAgendaResponse.getData().get(0);
        tvDetailAgenda.setText(Html.fromHtml(dataDetailAgenda.getDetail()));

        urlLetter = dataDetailAgenda.getSurat();
        urlSambutan = dataDetailAgenda.getSambutan();

        if (detailAgendaResponse.getData().get(0).getBtnDownloadSurat() == 1) {
            btnLetter.setVisibility(View.VISIBLE);
            btnLetter.setEnabled(urlLetter.charAt(urlLetter.length() - 1) != '-' && !urlLetter.isEmpty());
        } else {
            btnLetter.setVisibility(View.GONE);
        }

        if (detailAgendaResponse.getData().get(0).getBtnDownloadSambutan() == 1) {
            btnSambutan.setVisibility(View.VISIBLE);
            btnSambutan.setEnabled(urlSambutan.charAt(urlSambutan.length() - 1) != '-' && !urlSambutan.isEmpty());
        } else {
            btnSambutan.setVisibility(View.GONE);
        }

        if (detailAgendaResponse.getData().get(0).getTampilkanKomentar() == 1) {
            lineComment.setVisibility(View.VISIBLE);
            labelComment.setVisibility(View.VISIBLE);
            rvComment.setVisibility(View.VISIBLE);

            List<ListKomentarAgenda> listComment = detailAgendaResponse.getData().get(0).getListKomentar();

            if (listComment.size() > 3) {
                tvShowAllComment.setText(getResources().getString(R.string.show_other_comment, listComment.size() - 3));
                commentAdapter.setData(listComment.subList(0, 3));
            } else {
                tvShowAllComment.setText(getResources().getString(R.string.add_comment_text));
                commentAdapter.setData(listComment);
            }
            rvComment.setLayoutManager(new LinearLayoutManager(this));
            rvComment.setHasFixedSize(true);
            rvComment.setAdapter(commentAdapter);

        } else {
            lineComment.setVisibility(View.GONE);
            labelComment.setVisibility(View.GONE);
            rvComment.setVisibility(View.GONE);
            tvShowAllComment.setVisibility(View.GONE);
        }
    }

    @Override
    public void showFailureDetailAgenda(String message) {
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

    private boolean checkDirectoryAndFileExists(String fileName) {

        File outputDirectory;
        String path = Environment.getExternalStorageDirectory() + "/Download/E-Agenda";

        if (new CheckForSDCard().isSDCardPresent()) {
            outputDirectory = new File(path);
        } else {
            Toast.makeText(this, "Memori tidak ditemukan", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!outputDirectory.exists()) {
            outputDirectory.mkdir();
            return true;
        } else {
            File f = new File(path + "/" + fileName);

            if (f.exists()) {
                Toast.makeText(this, "File sudah ada\nLokasi file di Penyimpanan/Download/E-Agenda", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }

    private CharSequence noTrailingwhiteLines(CharSequence text) {

        while (text.charAt(text.length() - 1) == '\n') {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    public class CheckForSDCard {
        public boolean isSDCardPresent() {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Download/E-Agenda/" + fileName);

                byte[] data = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }


            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, "Download berhasil\nLokasi file di Penyimpanan/Download/E-Agenda", Toast.LENGTH_LONG).show();
            }
        }
    }
//
//    class ResultCallback extends ActivityResultContract<String, String> {
//
//        @NonNull
//        @Override
//        public Intent createIntent(@NonNull Context context, String input) {
//            Intent intent = new Intent(context, CommentActivity.class);
//            intent.putExtra(CODE, code);
//
//            retu
//        }
//
//        @Override
//        public String parseResult(int resultCode, @Nullable Intent intent) {
//            return null;
//        }
//    }
}
